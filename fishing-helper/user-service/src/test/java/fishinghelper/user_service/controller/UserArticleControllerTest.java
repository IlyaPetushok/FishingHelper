package fishinghelper.user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.ArticleDTOResponse;
import fishinghelper.user_service.dto.TagsDTO;
import fishinghelper.user_service.dto.filter.ArticleDTOFilter;
import fishinghelper.user_service.service.ArticleService;
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

import static org.mockito.Mockito.*;

@ContextConfiguration( classes = {UserArticleController.class})
@WebMvcTest(UserArticleController.class)
@ImportAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class UserArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService articleService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindArticleById() throws Exception {
        Integer articleId = 1;

        ArticleDTOResponse articleDTOResponse=new ArticleDTOResponse();

        when(articleService.findArticleById(articleId)).thenReturn(articleDTOResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/article/{id}", articleId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(articleService, times(1)).findArticleById(articleId);
    }

    @Test
    public void testFindArticleByTags() throws Exception {
        TagsDTO tagsDTO = new TagsDTO();
        List<ArticleDTOResponse> articleListDTO = List.of(new ArticleDTOResponse());
        when(articleService.findArticle(tagsDTO)).thenReturn(articleListDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/article/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(tagsDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

        verify(articleService, times(1)).findArticle(tagsDTO);
    }

    @Test
    public void testFindArticlesByFilter() throws Exception {
        FilterRequest filterRequest = new FilterRequest();
        List<ArticleDTOResponse> articleListDTO=List.of(new ArticleDTOResponse());
        when(articleService.showAllArticle(filterRequest)).thenReturn(articleListDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/article")
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

        verify(articleService, times(1)).showAllArticle(any(FilterRequest.class));
    }

    @Test
    public void testFindArticleByFilter() throws Exception {
        ArticleDTOFilter articleDTOFilter = new ArticleDTOFilter();

        when(articleService.showAllArticleFilter(articleDTOFilter)).thenReturn(List.of(new ArticleDTOResponse()));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/article/filter")
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

        verify(articleService, times(1)).showAllArticleFilter(any(ArticleDTOFilter.class));
    }
}
