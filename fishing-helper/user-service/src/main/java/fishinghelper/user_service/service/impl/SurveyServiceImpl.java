package fishinghelper.user_service.service.impl;

import fishinghelper.common_module.dao.PlaceRepositories;
import fishinghelper.common_module.dao.SurveyRepositories;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.common.Survey;
import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.FishDTO;
import fishinghelper.user_service.dto.SurveyDTORequest;
import fishinghelper.user_service.exception.PlaceNotFoundCustomException;
import fishinghelper.user_service.exception.UserNotFoundCustomException;
import fishinghelper.user_service.mapper.FishMapper;
import fishinghelper.user_service.mapper.SurveyMapper;
import fishinghelper.user_service.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Service class for managing operations related to surveys.
 * @author Ilya Petushok
 */
@Slf4j
@Service
public class SurveyServiceImpl implements SurveyService {
    private final SurveyMapper surveyMapper;
    private final FishMapper fishMapper;
    private final SurveyRepositories surveyRepositories;
    private final UserRepositories userRepositories;
    private final PlaceRepositories placeRepositories;

    public SurveyServiceImpl(SurveyMapper surveyMapper, FishMapper fishMapper, SurveyRepositories surveyRepositories, UserRepositories userRepositories, PlaceRepositories placeRepositories) {
        this.surveyMapper = surveyMapper;
        this.fishMapper = fishMapper;
        this.surveyRepositories = surveyRepositories;
        this.userRepositories = userRepositories;
        this.placeRepositories = placeRepositories;
    }

    /**
     * Creates a new survey based on the provided SurveyDTORequest and place ID.
     *
     * @param surveyDTORequest The SurveyDTORequest containing the survey information.
     * @param idPlace          The ID of the place associated with the survey.
     * @throws UserNotFoundCustomException   if no user is found with the specified ID.
     * @throws PlaceNotFoundCustomException  if no place is found with the specified ID.
     */
    @Override
    public void createSurvey(SurveyDTORequest surveyDTORequest, Integer idPlace) {
        log.info("Starting createSurvey method");
        log.info("Received survey request: {}", surveyDTORequest);
        log.info("Finding user with ID: {}", surveyDTORequest.getUserId());

        Survey survey = surveyMapper.toEntity(surveyDTORequest);
        User user = userRepositories.findById(surveyDTORequest.getUserId())
                .orElseThrow(() -> new UserNotFoundCustomException(HttpStatus.NOT_FOUND, "User not found"));

        log.info("Finding place with ID: {}", idPlace);

        Place place = placeRepositories.findById(idPlace)
                .orElseThrow(() -> new PlaceNotFoundCustomException(HttpStatus.NOT_FOUND, "Place not found by id"));
        place.setFish(updateListFishForPlace(place.getFish(), surveyDTORequest.getFishList()));
        placeRepositories.save(place);

        log.info("Saving updated place with ID: {}", place.getId());

        survey.setUser(user);
        survey.setPlace(place);
        surveyRepositories.save(survey);

        log.info("Survey created successfully with ID: {}", survey.getId());
    }

    /**
     * Updates the list of Fish associated with a Place by adding new FishDTOs to the existing list.
     *
     * @param fish      The current list of Fish associated with the Place.
     * @param fishDTOS  The list of FishDTOs to add to the Place.
     * @return The updated list of Fish associated with the Place after adding new Fish.
     */
    private List<Fish> updateListFishForPlace(List<Fish> fish, List<FishDTO> fishDTOS) {
        List<Fish> fishForUpdate = fishMapper.toEntities(fishDTOS);
        fishForUpdate.removeIf(f -> fish.stream()
                .anyMatch(fish1 -> Objects.equals(fish1.getId(), f.getId())));
        fish.addAll(fishForUpdate);
        return fish;
    }

    /**
     * Service method to find surveys based on the provided filter criteria.
     *
     * @param filterRequest The filter criteria for retrieving surveys, including pagination details.
     * @return A list of {@link SurveyDTORequest} objects representing the retrieved surveys.
     */
    @Override
    public List<SurveyDTORequest> findSurveyByFilter(FilterRequest filterRequest) {
        Page<Survey> surveyPage= surveyRepositories.findAll(filterRequest.getPageable());
        return surveyPage.stream()
                .map(surveyMapper::toDTO)
                .collect(Collectors.toList());
    }
}
