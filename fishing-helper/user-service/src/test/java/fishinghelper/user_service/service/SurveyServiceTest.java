package fishinghelper.user_service.service;

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
import fishinghelper.user_service.mapper.FishMapper;
import fishinghelper.user_service.mapper.SurveyMapper;
import fishinghelper.user_service.service.impl.SurveyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class SurveyServiceTest {
    @Mock
    private SurveyMapper surveyMapper;

    @Mock
    private UserRepositories userRepositories;

    @Mock
    private PlaceRepositories placeRepositories;

    @Mock
    private SurveyRepositories surveyRepositories;

    @Mock
    private FishMapper fishMapper;

    @InjectMocks
    private SurveyServiceImpl surveyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSurvey() {
        SurveyDTORequest surveyDTORequest = new SurveyDTORequest();
        surveyDTORequest.setUserId(1);
        surveyDTORequest.setFishList(new ArrayList<>());

        User user = new User();
        user.setId(1);

        Place place = new Place();
        place.setId(1);
        place.setFish(new ArrayList<>());

        Survey survey = new Survey();
        survey.setId(1);

        when(fishMapper.toEntities(anyList())).thenReturn(new ArrayList<>());
        when(surveyMapper.toEntity(any(SurveyDTORequest.class))).thenReturn(survey);
        when(userRepositories.findById(anyInt())).thenReturn(Optional.of(user));
        when(placeRepositories.findById(anyInt())).thenReturn(Optional.of(place));
        when(surveyRepositories.save(any(Survey.class))).thenReturn(survey);
        when(placeRepositories.save(any(Place.class))).thenReturn(place);

        surveyService.createSurvey(surveyDTORequest, 1);

        verify(userRepositories, times(1)).findById(anyInt());
        verify(placeRepositories, times(1)).findById(anyInt());
        verify(placeRepositories, times(1)).save(any(Place.class));
        verify(surveyRepositories, times(1)).save(any(Survey.class));
    }

    @Test
    void testFindSurveyByFilter() {
        FilterRequest filterRequest = new FilterRequest();
        Page<Survey> surveyPage = new PageImpl<>(Arrays.asList(new Survey(), new Survey()));
        List<SurveyDTORequest> surveyDTOs = Arrays.asList(new SurveyDTORequest(), new SurveyDTORequest());

        when(surveyRepositories.findAll(any(Pageable.class))).thenReturn(surveyPage);
        when(surveyMapper.toDTO(any(Survey.class))).thenReturn(new SurveyDTORequest());

        List<SurveyDTORequest> result = surveyService.findSurveyByFilter(filterRequest);

        assertEquals(surveyDTOs.size(), result.size());
        verify(surveyRepositories, times(1)).findAll(any(Pageable.class));
    }
}
