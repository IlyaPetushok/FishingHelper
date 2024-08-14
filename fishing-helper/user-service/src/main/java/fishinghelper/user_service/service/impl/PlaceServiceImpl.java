package fishinghelper.user_service.service.impl;


import fishinghelper.common_module.dao.OwnerRepositories;
import fishinghelper.common_module.dao.PlaceRepositories;
import fishinghelper.common_module.dao.SurveyRepositories;
import fishinghelper.common_module.entity.common.Status;
import fishinghelper.common_module.entity.common.Survey;
import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.common_module.entity.fish.Fish_;
import fishinghelper.common_module.entity.place.*;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.*;
import fishinghelper.user_service.dto.filter.PlaceDTOFilter;
import fishinghelper.user_service.exception.PlaceNotFoundCustomException;
import fishinghelper.user_service.exception.WeatherAPIServiceException;
import fishinghelper.user_service.mapper.PlaceMapper;
import fishinghelper.user_service.service.PlaceService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing operations related to places.
 *
 * @author Ilya Petushok
 */

@Slf4j
@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepositories placeRepositories;
    private final SurveyRepositories surveyRepositories;
    private final OwnerRepositories ownerRepositories;
    private final PlaceMapper placeMapper;
    private final RestTemplate restTemplate;


    @Autowired
    public PlaceServiceImpl(PlaceRepositories placeRepositories, SurveyRepositories surveyRepositories, OwnerRepositories ownerRepositories, PlaceMapper placeMapper, RestTemplate restTemplate) {
        this.placeRepositories = placeRepositories;
        this.surveyRepositories = surveyRepositories;
        this.ownerRepositories = ownerRepositories;
        this.placeMapper = placeMapper;
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves all places as PlaceDTOResponseAll objects.
     *
     * @return A list of PlaceDTOResponseAll representing all places.
     */

    @Override
    public List<PlaceDTOResponseAll> showAllPlaces() {
        return placeMapper.toDTOs(placeRepositories.findAllByStatus(Status.getStatus(Status.APPROVED)));
    }

    /**
     * Retrieves a list of all places based on the provided filter criteria.
     *
     * @param filterRequest The filter criteria for retrieving places.
     *                      Should include pagination details through {@link FilterRequest#getPageable()}.
     * @return A list of {@link PlaceDTOResponseAll} objects representing places that match the filter criteria.
     */
    @Override
    public List<PlaceDTOResponseAll> showAllPlaces(FilterRequest filterRequest) {
        log.debug("Fetching all places with filter request: {}", filterRequest);

        Page<Place> placePage = placeRepositories.findAll(filterRequest.getPageable());
        return placePage.stream()
                .map(placeMapper::toDTOResponseAll)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves a list of places based on the provided filter criteria.
     *
     * @param placeDTOFilter The filter criteria for retrieving places.
     * @return A list of {@link PlaceDTOResponseAll} objects representing places that match the filter criteria.
     */
    @Override
    public List<PlaceDTOResponseAll> showAllPlacesFilter(PlaceDTOFilter placeDTOFilter) {
        log.debug("Fetching places with filter: {}", placeDTOFilter);

        Specification<Place> placeSpecification = createSpecification(placeDTOFilter);
        Page<Place> placePage = placeRepositories.findAll(placeSpecification, placeDTOFilter.getPageable());
        return placePage.stream()
                .map(placeMapper::toDTOResponseAll)
                .collect(Collectors.toList());
    }


    /**
     * Creates a specification based on the provided filter criteria.
     *
     * @param placeDTOFilter The filter criteria to create the specification.
     * @return Specification object representing the filter criteria.
     */
    private Specification<Place> createSpecification(PlaceDTOFilter placeDTOFilter) {
        log.debug("Creating specification with filter: {}", placeDTOFilter);
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(placeDTOFilter.getPlaceType())) {
                Join<Place, TypePlace> typePlaceJoin = root.join(Place_.TYPE_PLACE);
                predicates.add(criteriaBuilder.like(typePlaceJoin.get(TypePlace_.NAME), "%" + placeDTOFilter.getPlaceType().getName() + "%"));
            }

            if (Objects.nonNull(placeDTOFilter.getFishNameList())) {
                Join<Place, Fish> placeFishJoin = root.join(Place_.FISH);
                predicates.add(placeFishJoin.get(Fish_.NAME).in(placeDTOFilter.getFishNameList()));
            }

            if (placeDTOFilter.getRatingFinish() != null && placeDTOFilter.getRatingStart() != null) {
                predicates.add(criteriaBuilder.between(root.get(Place_.RATING), placeDTOFilter.getRatingStart(), placeDTOFilter.getRatingFinish()));
            } else {
                if (placeDTOFilter.getRatingFinish() != null) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Place_.RATING), placeDTOFilter.getRatingFinish()));
                }
                if (placeDTOFilter.getRatingStart() != null) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Place_.RATING), placeDTOFilter.getRatingStart()));
                }
            }
            if (placeDTOFilter.isPaid()) {
                predicates.add(criteriaBuilder.equal(root.get(Place_.REQUIRE_PAYMENT), placeDTOFilter.isPaid()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Retrieves a specific place with its time to catch statistics.
     *
     * @param id The unique identifier of the place to retrieve.
     * @return A PlaceWithStatisticDTOResponse containing the place information and time to catch statistics.
     * @throws PlaceNotFoundCustomException if no place is found with the specified ID.
     */

    @CircuitBreaker(name = "handlerRefuseWeatherService",fallbackMethod = "showPlace")
    @Override
    public PlaceWithStatisticDTOResponse showPlace(Integer id) {
        Place place = placeRepositories.findById(id)
                .orElseThrow(() -> new PlaceNotFoundCustomException(HttpStatus.NOT_FOUND, "not found place by id"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WeatherPlaceDTORequest> httpEntity = new HttpEntity<>(new WeatherPlaceDTORequest(place.getCoordinates()), headers);

        try {
            ResponseEntity<WeatherPlaceDTOResponse> weather = restTemplate.postForEntity("http://localhost:8083/weather", httpEntity, WeatherPlaceDTOResponse.class);

            if (weather.getStatusCode() != HttpStatus.OK) {
                throw new PlaceNotFoundCustomException(HttpStatus.NOT_FOUND, "Place not found by location");
            }

            TimeToCatchFish timeToCatchFish = calculateStatisticTimeToCatch(surveyRepositories.findAllByPlaceId(id));
            return new PlaceWithStatisticDTOResponse(placeMapper.toDTO(place), timeToCatchFish, weather.getBody());
        } catch (ResourceAccessException exception) {
            throw new WeatherAPIServiceException(HttpStatus.NOT_FOUND,"Dont try connection weather service");
        }
    }

    public PlaceWithStatisticDTOResponse showPlace(Integer id,Throwable throwable){
        Place place = placeRepositories.findById(id)
                .orElseThrow(() -> new PlaceNotFoundCustomException(HttpStatus.NOT_FOUND, "not found place by id"));

        WeatherPlaceDTOResponse weatherPlaceDTOResponse=new WeatherPlaceDTOResponse();
        weatherPlaceDTOResponse.setTempMax(HttpStatus.NOT_FOUND.toString());
        weatherPlaceDTOResponse.setTempMin(WeatherAPIServiceException.class.getSimpleName());
        weatherPlaceDTOResponse.setTemp("Weather service unavailable");
        weatherPlaceDTOResponse.setSunRise("unavailable");
        weatherPlaceDTOResponse.setSunSet("unavailable");

        TimeToCatchFish timeToCatchFish = calculateStatisticTimeToCatch(surveyRepositories.findAllByPlaceId(id));

        return new PlaceWithStatisticDTOResponse(placeMapper.toDTO(place), timeToCatchFish, weatherPlaceDTOResponse);
    }

    /**
     * Calculates the time to catch statistics based on the provided list of surveys.
     *
     * @param surveyList The list of surveys to calculate statistics from.
     * @return A TimeToCatchFish object representing the calculated statistics.
     */
    @Override
    public TimeToCatchFish calculateStatisticTimeToCatch(List<Survey> surveyList) {
        log.info("Starting calculateStatisticTimeToCatch method");
        log.info("Received {} surveys for calculating time to catch statistics", surveyList.size());

        int count = 0;
        Map<TimeToCatch, Double> statisticMap = new HashMap<>();

        statisticMap.put(TimeToCatch.AFTERNOON, 0.0);
        statisticMap.put(TimeToCatch.MORNING, 0.0);
        statisticMap.put(TimeToCatch.EVENING, 0.0);

        for (Survey survey : surveyList) {
            if (survey.isAfternoon() || survey.isEvening() || survey.isMorning()) {
                count++;
                if (survey.isAfternoon()) {
                    statisticMap.replace(TimeToCatch.AFTERNOON, statisticMap.get(TimeToCatch.AFTERNOON) + 1);
                }
                if (survey.isEvening()) {
                    statisticMap.replace(TimeToCatch.EVENING, statisticMap.get(TimeToCatch.EVENING) + 1);
                }
                if (survey.isMorning()) {
                    statisticMap.replace(TimeToCatch.MORNING, statisticMap.get(TimeToCatch.MORNING) + 1);
                }
            }
        }

        log.info("Time to catch statistics calculated successfully");
        return TimeToCatchFish.builder()
                .morning(statisticMap.get(TimeToCatch.MORNING) * 100 / count)
                .afternoon(statisticMap.get(TimeToCatch.AFTERNOON) * 100 / count)
                .evening(statisticMap.get(TimeToCatch.EVENING) * 100 / count)
                .build();
    }

    /**
     * Creates a new place based on the provided PlaceDTORequest.
     *
     * @param placeDTORequest The PlaceDTORequest containing the information to create a place.
     */
    @Transactional(
            isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED
    )
    @Override
    public void createPlace(PlaceDTORequest placeDTORequest) {
        log.info("Starting createPlace method");
        log.info("Received request to create place: {}", placeDTORequest);

        Place place = placeMapper.toEntity(placeDTORequest);

        Owner owner = ownerRepositories.findOwnerByNumber(placeDTORequest.getOwner().getNumber());
        if (Objects.isNull(owner)) {
            owner = ownerRepositories.save(place.getOwner());
        }

        place.setOwner(owner);
        place.setRating(0);
        place.setStatus(Status.getStatus(Status.IN_PROCESSING));
        place.getPhotos().forEach(photo -> photo.setPlace(place));
        placeRepositories.save(place);

        log.info("Saved Place entity: {}", place);
    }
}
