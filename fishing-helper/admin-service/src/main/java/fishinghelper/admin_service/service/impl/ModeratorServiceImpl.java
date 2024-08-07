package fishinghelper.admin_service.service.impl;

import fishinghelper.admin_service.dto.*;
import fishinghelper.admin_service.exception.ArticleNotFoundException;
import fishinghelper.admin_service.exception.PlaceNotFoundException;
import fishinghelper.admin_service.mapper.ArticleMapper;
import fishinghelper.admin_service.mapper.MistakeMapper;
import fishinghelper.admin_service.mapper.PlaceMapper;
import fishinghelper.admin_service.service.ModeratorService;
import fishinghelper.common_module.dao.ArticleRepositories;
import fishinghelper.common_module.dao.MistakeRepositories;
import fishinghelper.common_module.dao.PlaceRepositories;
import fishinghelper.common_module.entity.common.Article;
import fishinghelper.common_module.entity.common.Mistake;
import fishinghelper.common_module.entity.common.Status;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.notification_service.config.RabbitConfig;
import fishinghelper.notification_service.messaging.producer.RabbitMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class for performing moderator operations on articles and places.
 * Provides methods for deletion, status update, and retrieval of articles and places data.
 * Also supports updating the approval status of articles and places based on moderator actions.
 *
 * @author Ilya Petushok
 */

@Slf4j
@Service
public class ModeratorServiceImpl implements ModeratorService {
    private final ArticleRepositories articleRepositories;
    private final PlaceRepositories placeRepositories;
    private final MistakeRepositories mistakeRepositories;
    private final ArticleMapper articleMapper;
    private final PlaceMapper placeMapper;
    private final MistakeMapper mistakeMapper;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public ModeratorServiceImpl(ArticleRepositories articleRepositories, PlaceRepositories placeRepositories, MistakeRepositories mistakeRepositories, ArticleMapper articleMapper, PlaceMapper placeMapper, MistakeMapper mistakeMapper, RabbitMQProducer rabbitMQProducer) {
        this.articleRepositories = articleRepositories;
        this.placeRepositories = placeRepositories;
        this.mistakeRepositories = mistakeRepositories;
        this.articleMapper = articleMapper;
        this.placeMapper = placeMapper;
        this.mistakeMapper = mistakeMapper;
        this.rabbitMQProducer = rabbitMQProducer;
    }


    /**
     * Deletes a place by its identifier.
     *
     * @param id Identifier of the place to delete
     */
    @Override
    public void deletePlace(Integer id) {
        placeRepositories.deleteById(id);
    }

    /**
     * Deletes an article by its identifier.
     *
     * @param id Identifier of the article to delete
     */
    @Override
    public void deleteArticle(Integer id) {
        articleRepositories.deleteById(id);
    }

    /**
     * Retrieves a list of DTOs representing articles with the specified status.
     *
     * @param status Status of articles to retrieve
     * @return List of article DTOs
     */
    @Override
    public List<ArticleDTOResponse> showArticleByStatus(String status, FilterRequest filterRequest) {
        return articleMapper.toDTOsResponse(articleRepositories.findAllByStatus(Status.getStatus(Status.valueOf(status)),filterRequest.getPageable()));
    }

    /**
     * Retrieves a list of DTOs representing places with the specified status.
     *
     * @param status Status of places to retrieve
     * @return List of place DTOs
     */
    @Override
    public List<PlaceDTOResponse> showPlaceByStatus(String status,FilterRequest filterRequest) {
        return placeMapper.toDTOsResponse(placeRepositories.findAllByStatus(Status.getStatus(Status.valueOf(status)),filterRequest.getPageable()));
    }

    /**
     * Updates an article identified by the given ID with data from the provided DTO.
     *
     * @param articleDTO DTO containing updated article data
     * @param id         Identifier of the article to update
     */
    @Override
    public void updateArticle(ArticleDTO articleDTO, Integer id) {
        log.info("admin service start update article");

        Article article = articleRepositories.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(HttpStatus.NOT_FOUND, "article not found by id for update"));

        log.info("article find buy id {} for update", id);

        articleMapper.updateArticleFromDTO(article, articleDTO);
        articleRepositories.save(article);
    }

    /**
     * Updates a place identified by the given ID with data from the provided DTO.
     *
     * @param placeDTO DTO containing updated place data
     * @param id       Identifier of the place to update
     */
    @Override
    public void updatePlace(PlaceDTO placeDTO, Integer id) {
        log.info("admin service start update place");

        Place place = placeRepositories.findById(id)
                .orElseThrow(() -> new PlaceNotFoundException(HttpStatus.NOT_FOUND, "place not found by id for update"));

        log.info("place find buy id {} for update", id);

        placeMapper.updatePlaceFromDTO(placeDTO, place);
        placeRepositories.save(place);
    }

    /**
     * Updates the approval status of an entity (article or place) based on the moderator's action.
     *
     * @param redactorDTO DTO containing moderator's action data
     * @param id          Identifier of the entity to update
     * @param entityType  Type of the entity (article or place)
     */

    @Override
    public void updateEntityStatus(RedactorDTO redactorDTO, Integer id, EntityType entityType) {
        log.info("Admin service start updating {} by status", entityType);

        switch (redactorDTO.getStatus()) {
            case "неодобренно", "удаленно" -> {
                log.info("{} not approved", entityType);

                Mistake mistake=mistakeMapper.toEntity(redactorDTO.getMistakeDTO());
                createMistakeForStatus(entityType,mistake,id);
            }
            case "одобренно" -> {
                log.info("{} approved", entityType);

                statusUpdateApproved(entityType,id);
            }
        }
    }

    /**
     * Creates a mistake record for an entity (article or place) based on the moderator's action.
     *
     * @param entityType Type of the entity (article or place)
     * @param mistake    Mistake entity to save
     * @param id         Identifier of the entity to link the mistake
     */

    private void createMistakeForStatus(EntityType entityType,Mistake mistake,Integer id){
        switch (entityType) {
            case ARTICLE -> {
                Article article = articleRepositories.findById(id)
                        .orElseThrow(() -> new ArticleNotFoundException(HttpStatus.NOT_FOUND, "Article not found by ID for update"));

                mistake.setArticle(article);
                mistakeRepositories.save(mistake);

                article.setStatus(Status.getStatus(Status.NOT_APPROVED));
                articleRepositories.save(article);
            }
            case PLACE -> {
                Place place = placeRepositories.findById(id)
                        .orElseThrow(() -> new PlaceNotFoundException(HttpStatus.NOT_FOUND, "Place not found by ID for update"));

                mistake.setPlace(place);
                mistakeRepositories.save(mistake);

                place.setStatus(Status.getStatus(Status.NOT_APPROVED));
                placeRepositories.save(place);
            }
        }
    }

    /**
     * Updates the approval status of an entity (article or place) to approved.
     *
     * @param entityType Type of the entity (article or place)
     * @param id         Identifier of the entity to update
     */
    private void statusUpdateApproved(EntityType entityType,Integer id){
        switch (entityType) {
            case ARTICLE -> {
                Article article = articleRepositories.findById(id)
                        .orElseThrow(() -> new ArticleNotFoundException(HttpStatus.NOT_FOUND, "Article not found by ID for update"));
                article.setStatus(Status.getStatus(Status.APPROVED));
                articleRepositories.save(article);
                if(article.isImportance()){
                    rabbitMQProducer.sendMessageQueue("Important notification"+ article.getName()+",link:"+"http:/localhost:9999/article"+article.getId(), RabbitConfig.ROUTING_KEY_1);
                }
            }
            case PLACE -> {
                Place place = placeRepositories.findById(id)
                        .orElseThrow(() -> new PlaceNotFoundException(HttpStatus.NOT_FOUND, "Place not found by ID for update"));
                place.setStatus(Status.getStatus(Status.APPROVED));
                placeRepositories.save(place);
            }
        }
    }


}
