package fishinghelper.user_service.service;

import fishinghelper.common_module.dao.OwnerRepositories;
import fishinghelper.common_module.dao.PlaceRepositories;
import fishinghelper.common_module.dao.SurveyRepositories;
import fishinghelper.common_module.entity.common.Status;
import fishinghelper.common_module.entity.common.Survey;
import fishinghelper.common_module.entity.place.Owner;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.*;
import fishinghelper.user_service.dto.filter.PlaceDTOFilter;
import fishinghelper.user_service.mapper.PlaceMapper;
import fishinghelper.user_service.service.impl.PlaceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class PlaceServiceTest {

    @Mock
    private PlaceMapper placeMapper;

    @Mock
    private PlaceRepositories placeRepositories;

    @Mock
    private SurveyRepositories surveyRepositories;

    @Mock
    private OwnerRepositories ownerRepositories;

    @InjectMocks
    private PlaceServiceImpl placeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowAllPlaces() {
        List<Place> places = List.of(new Place(), new Place());
        List<PlaceDTOResponseAll> placeDTOs =List.of(new PlaceDTOResponseAll(), new PlaceDTOResponseAll());

        when(placeRepositories.findAllByStatus(any())).thenReturn(places);
        when(placeMapper.toDTOs(anyList())).thenReturn(placeDTOs);

        List<PlaceDTOResponseAll> result = placeService.showAllPlaces();

        assertEquals(placeDTOs, result);
        verify(placeRepositories, times(1)).findAllByStatus(Status.getStatus(Status.APPROVED));
    }

    @Test
    void testShowAllPlacesWithFilter() {
        FilterRequest filterRequest = new FilterRequest();
        Page<Place> placePage = new PageImpl<>(List.of(new Place()));
        PlaceDTOResponseAll placeDTOs = new PlaceDTOResponseAll();

        when(placeRepositories.findAll(any(Pageable.class))).thenReturn(placePage);
        when(placeMapper.toDTOResponseAll(any())).thenReturn(placeDTOs);

        List<PlaceDTOResponseAll> result = placeService.showAllPlaces(filterRequest);

        assertEquals(List.of(placeDTOs), result);
        verify(placeRepositories, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testShowAllPlacesFilter() {
        PlaceDTOFilter placeDTOFilter = new PlaceDTOFilter();
        Page<Place> placePage = new PageImpl<>(List.of(new Place()));
        PlaceDTOResponseAll placeDTOs = new PlaceDTOResponseAll();

        when(placeRepositories.findAll(any(Specification.class),any(Pageable.class))).thenReturn(placePage);
        when(placeMapper.toDTOResponseAll(any())).thenReturn(placeDTOs);

        List<PlaceDTOResponseAll> result = placeService.showAllPlacesFilter(placeDTOFilter);

        assertEquals(List.of(placeDTOs), result);
        verify(placeRepositories, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testShowPlace() {
        Integer placeId = 1;
        Place place = new Place();
        TimeToCatchFish timeToCatchFish = new TimeToCatchFish();
        PlaceWithStatisticDTOResponse dtoResponse = new PlaceWithStatisticDTOResponse(new PlaceDTOResponse(), timeToCatchFish);

        when(placeRepositories.findById(anyInt())).thenReturn(Optional.of(place));
        when(surveyRepositories.findAllByPlaceId(anyInt())).thenReturn(new ArrayList<>());
        when(placeMapper.toDTO(any())).thenReturn(new PlaceDTOResponse());

        PlaceWithStatisticDTOResponse result = placeService.showPlace(placeId);
        result.setTimeToCatchFish(timeToCatchFish);

        assertEquals(dtoResponse, result);
        verify(placeRepositories, times(1)).findById(anyInt());
        verify(surveyRepositories, times(1)).findAllByPlaceId(anyInt());
    }

    @Test
    void testCalculateStatisticTimeToCatch() {
        Survey survey1=new Survey();
        survey1.setAfternoon(false);
        survey1.setEvening(false);
        survey1.setMorning(true);
        Survey survey2=new Survey();
        survey2.setMorning(false);
        survey2.setAfternoon(true);
        survey2.setEvening(false);

        List<Survey> surveys = List.of(survey1,survey2);
        TimeToCatchFish timeToCatchFish = TimeToCatchFish.builder()
                .morning(50)
                .afternoon(50)
                .evening(0)
                .build();

        TimeToCatchFish result = placeService.calculateStatisticTimeToCatch(surveys);

        assertEquals(timeToCatchFish, result);
    }

    @Test
    void testCreatePlace() {
        PlaceDTORequest placeDTORequest = new PlaceDTORequest();
        Place place = new Place();
        Owner owner = new Owner();
        placeDTORequest.setOwner(new OwnerDTO());

        when(placeMapper.toEntity(any(PlaceDTORequest.class))).thenReturn(place);
        when(ownerRepositories.findOwnerByNumber(anyString())).thenReturn(null);
        when(ownerRepositories.save(any(Owner.class))).thenReturn(owner);

        placeService.createPlace(placeDTORequest);

        verify(placeRepositories, times(1)).save(place);
    }
}
