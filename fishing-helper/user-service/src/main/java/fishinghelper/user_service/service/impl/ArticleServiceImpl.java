package fishinghelper.user_service.service.impl;

import fishinghelper.common_module.dao.ArticleRepositories;
import fishinghelper.common_module.dao.TagsRepositories;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.common.*;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.user_service.dto.ArticleDTORequest;
import fishinghelper.user_service.dto.ArticleDTOResponse;
import fishinghelper.user_service.dto.TagsDTO;
import fishinghelper.user_service.dto.filter.ArticleDTOFilter;
import fishinghelper.user_service.exception.ArticleNotFoundException;
import fishinghelper.user_service.exception.UserNotFoundCustomException;
import fishinghelper.user_service.mapper.ArticleMapper;
import fishinghelper.user_service.service.ArticleService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service class responsible for creating and retrieving articles.
 *
 * @author Ilya Petushok
 */

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    private final TagsRepositories tagsRepositories;
    private final UserRepositories userRepositories;
    private final ArticleRepositories articleRepositories;
    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleServiceImpl(TagsRepositories tagsRepositories, UserRepositories userRepositories, ArticleRepositories articleRepositories, ArticleMapper articleMapper) {
        this.tagsRepositories = tagsRepositories;
        this.userRepositories = userRepositories;
        this.articleRepositories = articleRepositories;
        this.articleMapper = articleMapper;
    }

    /**
     * Creates a new article based on the provided DTO.
     *
     * @param articleDTORequest The DTO containing article information to be created.
     * @throws UserNotFoundCustomException if the user with the specified ID is not found.
     */
    @Override
    public void createArticle(ArticleDTORequest articleDTORequest) {
        log.info("start service article for create");
        log.info("Received request to create article: {}", articleDTORequest);

        Article article = articleMapper.toEntity(articleDTORequest);
        User user = userRepositories.findById(articleDTORequest.getIdUser())
                .orElseThrow(() -> new UserNotFoundCustomException(HttpStatus.NOT_FOUND, "User not found for create article"));

        article.setUser(user);
        article.setStatus(Status.getStatus(Status.IN_PROCESSING));

        tagsRepositories.saveAll(article.getTagsList());
        articleRepositories.save(article);

        log.info("Article created successfully with ID: {}", article.getId());
    }


    /**
     * Retrieves a list of articles based on the provided tags DTO.
     *
     * @param tagsDTO The DTO containing tags for filtering articles.
     * @return A list of ArticleDTOResponse objects mapped from the retrieved articles.
     */
    @Override
    public List<ArticleDTOResponse> findArticle(TagsDTO tagsDTO) {
        log.info("Starting service method: findArticle");
        log.info("Received request to find articles by tags: {}", tagsDTO.getName());

        List<Article> articles = articleRepositories.findByTags(tagsDTO.getName());
        return articleMapper.toDTOs(articles);
    }


    /**
     * Retrieves an ArticleDTOResponse based on the provided article ID.
     *
     * @param id The unique identifier of the article to retrieve.
     * @return An ArticleDTOResponse object mapped from the retrieved article entity.
     * @throws ArticleNotFoundException if no article exists with the specified ID.
     */
    @Override
    public ArticleDTOResponse findArticleById(Integer id) {
        Article article = articleRepositories.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(HttpStatus.NOT_FOUND, "article not foud by id {" + id + "}"));
        return articleMapper.toDTO(article);
    }

    /**
     * Retrieves all articles based on the provided filter request.
     *
     * @param filterRequest The filter criteria for retrieving articles, including pagination details.
     * @return A list of {@link ArticleDTOResponse} objects representing the retrieved articles.
     */
    @Override
    public List<ArticleDTOResponse> showAllArticle(FilterRequest filterRequest) {
        log.debug("Fetching all articles with filter: {}", filterRequest);

        Page<Article> articlePage = articleRepositories.findAll(filterRequest.getPageable());
        return articlePage.stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves articles based on the provided filter criteria.
     *
     * @param articleDTOFilter The filter criteria for retrieving articles, including tags and importance.
     * @return A list of {@link ArticleDTOResponse} objects representing the retrieved articles.
     */
    @Override
    public List<ArticleDTOResponse> showAllArticleFilter(ArticleDTOFilter articleDTOFilter) {
        log.debug("Fetching articles with filter: {}", articleDTOFilter);

        Specification<Article> articleSpecification = createSpecification(articleDTOFilter);
        Page<Article> articlePage = articleRepositories.findAll(articleSpecification, articleDTOFilter.getPageable());
        return articlePage.stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
    }


    /**
     * Creates a specification for filtering articles based on the provided filter criteria.
     *
     * @param articleDTOFilter The filter criteria for articles, including tags and importance.
     * @return A Specification object representing the criteria for filtering articles.
     */
    private Specification<Article> createSpecification(ArticleDTOFilter articleDTOFilter) {
        log.debug("Creating specification with filter: {}", articleDTOFilter);

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(articleDTOFilter.getName()) && !articleDTOFilter.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get(Article_.NAME), "%" + articleDTOFilter.getName() + "%"));
            }

            if (articleDTOFilter.getTags() != null) {
                Join<Article, Tags> articleTagsJoin = root.join(Article_.TAGS_LIST);
                predicates.add(articleTagsJoin.get(Tags_.NAME).in(articleDTOFilter.getTags()));
            }
            if (articleDTOFilter.isImportance()) {
                predicates.add(criteriaBuilder.equal(root.get(Article_.IMPORTANCE), articleDTOFilter.isImportance()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

