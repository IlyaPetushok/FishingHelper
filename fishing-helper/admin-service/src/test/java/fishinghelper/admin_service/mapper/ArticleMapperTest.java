package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.ArticleDTO;
import fishinghelper.admin_service.dto.ArticleDTOResponse;
import fishinghelper.common_module.entity.common.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ArticleMapperImpl.class})
public class ArticleMapperTest {
    @Autowired
    private ArticleMapper articleMapper;
    
    @Test
    public void testToDTO() {
        Article article = new Article();
        article.setId(1);
        article.setName("Test Article");

        ArticleDTO articleDTO = articleMapper.toDTO(article);

        assertNotNull(articleDTO);
        assertEquals(article.getId(), articleDTO.getId());
        assertEquals(article.getName(), articleDTO.getName());
    }

    @Test
    public void testToEntity() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(1);
        articleDTO.setName("Test Article");

        Article article = articleMapper.toEntity(articleDTO);

        assertNotNull(article);
        assertEquals(articleDTO.getId(), article.getId());
        assertEquals(articleDTO.getName(), article.getName());
    }

    @Test
    public void testToDTOsResponse() {
        List<Article> articles = new ArrayList<>();
        Article article1 = new Article();
        article1.setId(1);
        article1.setName("Test Article 1");
        articles.add(article1);

        Article article2 = new Article();
        article2.setId(2);
        article2.setName("Test Article 2");
        articles.add(article2);

        List<ArticleDTOResponse> dtoResponses = articleMapper.toDTOsResponse(articles);

        assertNotNull(dtoResponses);
        assertEquals(2, dtoResponses.size());
        assertEquals(article1.getName(), dtoResponses.get(0).getName());
        assertEquals(article2.getName(), dtoResponses.get(1).getName());
    }

    @Test
    public void testUpdateArticleFromDTO() {
        Article article = new Article();
        article.setId(1);
        article.setName("Old Name");

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName("New Name");

        articleMapper.updateArticleFromDTO(article, articleDTO);

        assertEquals(articleDTO.getName(), article.getName());
    }
}
