package fishinghelper.admin_service.controller;

import fishinghelper.admin_service.dto.*;
import fishinghelper.admin_service.service.ModeratorService;
import fishinghelper.common_module.filter.FilterRequest;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration( classes = {ModeratorController.class})
@WebMvcTest(ModeratorController.class)
@ImportAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class ModeratorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ModeratorService moderatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testShowArticles_Success() throws Exception {
        String jsonRequest = "{\"filter\":\"value\"}";
        when(moderatorService.showArticleByStatus(any(String.class), any(FilterRequest.class)))
                .thenReturn(List.of(new ArticleDTOResponse())); // Adjust the return type as necessary

        mockMvc.perform(get("/redactor/article/active")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateStatusArticle_Success() throws Exception {
        String jsonRequest = "{\"status\":\"active\"}";

        mockMvc.perform(post("/redactor/article/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(moderatorService).updateEntityStatus(any(RedactorDTO.class), anyInt(), any(EntityType.class));
    }

    @Test
    public void testUpdateArticle_Success() throws Exception {
        String jsonRequest = "{\"title\":\"New Title\"}";

        mockMvc.perform(post("/redactor/article/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(moderatorService).updateArticle(any(ArticleDTO.class), anyInt());
    }

    @Test
    public void testDeleteArticle_Success() throws Exception {
        mockMvc.perform(get("/article/1/delete"))
                .andExpect(status().isOk());

        verify(moderatorService).deleteArticle(anyInt());
    }

    @Test
    public void testUpdateStatusPlace_Success() throws Exception {
        String jsonRequest = "{\"filter\":\"value\"}";

        mockMvc.perform(get("/redactor/place/active")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(moderatorService).showPlaceByStatus(any(String.class), any(FilterRequest.class));
    }

    @Test
    public void testUpdatePlace_Success() throws Exception {
        String jsonRequest = "{\"location\":\"New Location\"}";

        mockMvc.perform(post("/redactor/place/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(moderatorService).updatePlace(any(PlaceDTO.class), anyInt());
    }

    @Test
    public void testDeletePlace_Success() throws Exception {
        mockMvc.perform(get("/place/1/delete"))
                .andExpect(status().isOk());

        verify(moderatorService).deletePlace(anyInt());
    }
}
