package fishinghelper.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.PlaceDTORequest;
import fishinghelper.user_service.dto.SurveyDTORequest;
import fishinghelper.user_service.service.PlaceService;
import fishinghelper.user_service.service.SurveyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration( classes = {AuthorController.class})
@WebMvcTest(AuthorController.class)
@ImportAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class AuthorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PlaceService placeService;

    @MockBean
    SurveyService surveyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePlace() throws Exception {
        PlaceDTORequest placeDTO = new PlaceDTORequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/create/place")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(placeDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        verify(placeService, times(1)).createPlace(any(PlaceDTORequest.class));
    }

    @Test
    public void testCreateSurvey() throws Exception {
        SurveyDTORequest surveyDTO = new SurveyDTORequest();
        Integer placeId = 1;

        mockMvc.perform(MockMvcRequestBuilders.post("/place/{id}/survey", placeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(surveyDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        verify(surveyService, times(1)).createSurvey(any(SurveyDTORequest.class), eq(placeId));
    }

    @Test
    public void testSurveyFindByFilter() throws Exception {
        FilterRequest filterRequest = new FilterRequest();

        when(surveyService.findSurveyByFilter(any(FilterRequest.class))).thenReturn(List.of(new SurveyDTORequest()));

        System.out.println(new ObjectMapper().writeValueAsString(filterRequest));

        mockMvc.perform(MockMvcRequestBuilders.post("/survey/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "    \"page\":0,\n" +
                                "    \"size\":3,\n" +
                                "    \"sortDirection\":\"asc\",\n" +
                                "    \"sortByName\":\"id\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(surveyService, times(1)).findSurveyByFilter(any(FilterRequest.class));
    }
}
