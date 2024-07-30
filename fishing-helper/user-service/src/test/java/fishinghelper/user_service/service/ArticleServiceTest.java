package fishinghelper.user_service.service;


import fishinghelper.common_module.dao.ArticleRepositories;
import fishinghelper.common_module.dao.TagsRepositories;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.common.Article;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.ArticleDTORequest;
import fishinghelper.user_service.dto.ArticleDTOResponse;
import fishinghelper.user_service.dto.TagsDTO;
import fishinghelper.user_service.dto.filter.ArticleDTOFilter;
import fishinghelper.user_service.mapper.ArticleMapper;
import fishinghelper.user_service.service.impl.ArticleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class ArticleServiceTest {
    @Mock
    private TagsRepositories tagsRepositories;

    @Mock
    private UserRepositories userRepositories;

    @Mock
    private ArticleRepositories articleRepositories;

    @Mock
    private ArticleMapper articleMapper;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateArticle() {
        ArticleDTORequest articleDTORequest = new ArticleDTORequest();
        articleDTORequest.setIdUser(1);
        articleDTORequest.setName("Test Title");

        User user = new User();
        user.setId(1);

        Article article = new Article();
        article.setId(1);
        article.setUser(user);

        when(userRepositories.findById(anyInt())).thenReturn(Optional.of(user));
        when(articleMapper.toEntity(any(ArticleDTORequest.class))).thenReturn(article);
        when(articleMapper.toDTO(any(Article.class))).thenReturn(new ArticleDTOResponse());

        articleService.createArticle(articleDTORequest);

        verify(articleRepositories, times(1)).save(article);
        verify(tagsRepositories, times(1)).saveAll(article.getTagsList());
    }

    @Test
    void testFindArticle() {
        TagsDTO tagsDTO = new TagsDTO();

        Article article = new Article();
        article.setId(1);

        List<Article> articles = Collections.singletonList(article);
        when(articleRepositories.findByTags(anyList())).thenReturn(articles);
        when(articleMapper.toDTOs(anyList())).thenReturn(Collections.singletonList(new ArticleDTOResponse()));

        List<ArticleDTOResponse> result = articleService.findArticle(tagsDTO);

        Assertions.assertFalse(result.isEmpty());
        verify(articleRepositories, times(1)).findByTags(tagsDTO.getName());
    }

    @Test
    void testFindArticleById() {
        Article article = new Article();
        article.setId(1);

        when(articleRepositories.findById(anyInt())).thenReturn(Optional.of(article));
        when(articleMapper.toDTO(any(Article.class))).thenReturn(new ArticleDTOResponse());

        ArticleDTOResponse result = articleService.findArticleById(1);

        Assertions.assertNotNull(result);
        verify(articleRepositories, times(1)).findById(1);
    }

    @Test
    void testShowAllArticle() {
        FilterRequest filterRequest = new FilterRequest();
//        Pageable pageable = filterRequest.getPageable();

        Article article = new Article();
        article.setId(1);
        Page<Article> articlePage = new PageImpl<>(Collections.singletonList(article), filterRequest.getPageable(), 1);

        when(articleRepositories.findAll(any(Pageable.class))).thenReturn(articlePage);
        when(articleMapper.toDTO(any(Article.class))).thenReturn(new ArticleDTOResponse());

        List<ArticleDTOResponse> result = articleService.showAllArticle(filterRequest);

        Assertions.assertFalse(result.isEmpty());
        verify(articleRepositories, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testShowAllArticleFilter() {
        ArticleDTOFilter filter = new ArticleDTOFilter();

        Article article = new Article();
        article.setId(1);
        Page<Article> articlePage = new PageImpl<>(Collections.singletonList(article), filter.getPageable(), 1);

        when(articleRepositories.findAll(any(Specification.class), any(Pageable.class))).thenReturn(articlePage);
        when(articleMapper.toDTO(any(Article.class))).thenReturn(new ArticleDTOResponse());

        List<ArticleDTOResponse> result = articleService.showAllArticleFilter(filter);

        Assertions.assertFalse(result.isEmpty());
        verify(articleRepositories, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}
