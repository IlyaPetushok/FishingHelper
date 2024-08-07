package fishinghelper.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.CommentDTO;
import fishinghelper.user_service.dto.PlaceDTOResponseAll;
import fishinghelper.user_service.dto.PlaceWithStatisticDTOResponse;
import fishinghelper.user_service.dto.filter.PlaceDTOFilter;
import fishinghelper.user_service.service.ArticleService;
import fishinghelper.user_service.service.CommentsService;
import fishinghelper.user_service.service.PlaceService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserPlaceController.class})
@WebMvcTest(UserPlaceController.class)
@ImportAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class UserPlaceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PlaceService placeService;

    @MockBean
    CommentsService commentsService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowAllPlaces() throws Exception {
        when(placeService.showAllPlaces()).thenReturn(List.of(new PlaceDTOResponseAll()));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/place")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(placeService, times(1)).showAllPlaces();
    }

    @Test
    public void testShowPlace() throws Exception {
        Integer placeId = 1;
        when(placeService.showPlace(placeId)).thenReturn(new PlaceWithStatisticDTOResponse());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/place/{id}", placeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(placeService, times(1)).showPlace(placeId);
    }

    @Test
    public void testCreateComment() throws Exception {
        Integer placeId = 1;
        CommentDTO commentDTO = new CommentDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/user/place/{id}/comment", placeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        verify(commentsService, times(1)).createCommentForPlace(commentDTO, placeId);
    }

    @Test
    public void testShowComments() throws Exception {
        Integer placeId = 1;

        when(commentsService.showComments(anyInt(), any(FilterRequest.class))).thenReturn(List.of(new CommentDTO()));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/place/{id}/comments", placeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "    \"page\":0,\n" +
                                "    \"size\":3,\n" +
                                "    \"sortDirection\":\"asc\",\n" +
                                "    \"sortByName\":\"id\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(commentsService, times(1)).showComments(anyInt(),any(FilterRequest.class));
    }

    @Test
    public void testFindPlaceByFilter() throws Exception {
        when(placeService.showAllPlaces(any(FilterRequest.class))).thenReturn(List.of(new PlaceDTOResponseAll()));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/place")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "    \"page\":0,\n" +
                                "    \"size\":3,\n" +
                                "    \"sortDirection\":\"asc\",\n" +
                                "    \"sortByName\":\"id\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(placeService, times(1)).showAllPlaces(any(FilterRequest.class));
    }

    @Test
    public void testFindPlaceByFilterUsingPlaceDTOFilter() throws Exception {

        when(placeService.showAllPlacesFilter(any(PlaceDTOFilter.class))).thenReturn(List.of(new PlaceDTOResponseAll()));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/place/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "    \"page\":0,\n" +
                                "    \"size\":3,\n" +
                                "    \"sortDirection\":\"asc\",\n" +
                                "    \"sortByName\":\"id\"\n" +
                                "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(placeService, times(1)).showAllPlacesFilter(any(PlaceDTOFilter.class));
    }
}
