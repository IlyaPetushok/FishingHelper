package fishinghelper.user_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.user_service.dto.ArticleDTORequest;
import fishinghelper.user_service.service.ArticleService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration( classes = {JournalistController.class})
@WebMvcTest(JournalistController.class)
@ImportAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class JournalistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService articleService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateArticle() throws Exception {
        ArticleDTORequest articleDTO = new ArticleDTORequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/create/article")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(articleDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        verify(articleService, times(1)).createArticle(any(ArticleDTORequest.class));
    }
}
