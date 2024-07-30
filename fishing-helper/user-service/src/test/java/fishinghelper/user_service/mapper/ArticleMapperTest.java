package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.common.Article;
import fishinghelper.user_service.dto.ArticleDTORequest;
import fishinghelper.user_service.dto.ArticleDTOResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest(classes = {ArticleMapperImpl.class})
public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void testToEntity() {
        ArticleDTORequest dtoRequest = new ArticleDTORequest();
        dtoRequest.setName("Title");
        dtoRequest.setDescription("Content");

        Article article = articleMapper.toEntity(dtoRequest);

        assertNotNull(article);
        assertEquals("Title", article.getName());
        assertEquals("Content", article.getDescription());
    }

    @Test
    public void testToDTO() {
        Article article = new Article();
        article.setName("Title");
        article.setDescription("Content");

        ArticleDTOResponse dtoResponse = articleMapper.toDTO(article);

        assertNotNull(dtoResponse);
        assertEquals("Title", dtoResponse.getName());
        assertEquals("Content", dtoResponse.getDescription());
    }

    @Test
    public void testToDTOs() {
        Article article1 = new Article();
        article1.setName("Title1");
        article1.setDescription("Content1");

        Article article2 = new Article();
        article2.setId(2);
        article2.setName("Title2");
        article2.setDescription("Content2");

        List<Article> articles = List.of(article1, article2);

        List<ArticleDTOResponse> dtoResponses = articleMapper.toDTOs(articles);

        assertNotNull(dtoResponses);
        assertEquals(2, dtoResponses.size());

        ArticleDTOResponse dto1 = dtoResponses.get(0);
        assertEquals("Title1", dto1.getName());
        assertEquals("Content1", dto1.getDescription());

        ArticleDTOResponse dto2 = dtoResponses.get(1);
        assertEquals("Title2", dto2.getName());
        assertEquals("Content2", dto2.getDescription());
    }
}
