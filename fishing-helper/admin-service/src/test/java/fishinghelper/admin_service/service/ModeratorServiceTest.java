package fishinghelper.admin_service.service;

import fishinghelper.admin_service.dto.*;
import fishinghelper.admin_service.mapper.ArticleMapper;
import fishinghelper.admin_service.mapper.MistakeMapper;
import fishinghelper.admin_service.mapper.PlaceMapper;
import fishinghelper.admin_service.service.impl.ModeratorServiceImpl;
import fishinghelper.common_module.dao.ArticleRepositories;
import fishinghelper.common_module.dao.MistakeRepositories;
import fishinghelper.common_module.dao.PlaceRepositories;
import fishinghelper.common_module.entity.common.Article;
import fishinghelper.common_module.entity.common.Mistake;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.filter.FilterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ModeratorServiceTest {
    @Mock
    private PlaceRepositories placeRepositories;

    @Mock
    private ArticleRepositories articleRepositories;

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private PlaceMapper placeMapper;

    @Mock
    private MistakeRepositories mistakeRepositories;

    @Mock
    private MistakeMapper mistakeMapper;

    @InjectMocks
    private ModeratorServiceImpl moderatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeletePlace() {
        Integer placeId = 1;
        doNothing().when(placeRepositories).deleteById(placeId);

        moderatorService.deletePlace(placeId);

        verify(placeRepositories, times(1)).deleteById(placeId);
    }

    @Test
    void testDeleteArticle() {
        Integer articleId = 1;
        doNothing().when(articleRepositories).deleteById(articleId);

        moderatorService.deleteArticle(articleId);

        verify(articleRepositories, times(1)).deleteById(articleId);
    }

    @Test
    void testShowArticleByStatus() {
        String status = "APPROVED";
        FilterRequest filterRequest = new FilterRequest();
        ArticleDTOResponse articleDTOResponse = new ArticleDTOResponse();
        Article article = new Article();
        List<Article> articles = List.of(article);

        when(articleRepositories.findAllByStatus(any(), any(Pageable.class))).thenReturn(articles);
        when(articleMapper.toDTOsResponse(any())).thenReturn(List.of(articleDTOResponse));

        List<ArticleDTOResponse> result = moderatorService.showArticleByStatus(status, filterRequest);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(articleRepositories, times(1)).findAllByStatus(any(), any(Pageable.class));
    }

    @Test
    void testShowPlaceByStatus() {
        String status = "APPROVED";
        FilterRequest filterRequest = new FilterRequest();
        PlaceDTOResponse placeDTOResponse = new PlaceDTOResponse();
        Place place = new Place();
        List<Place> places = List.of(place);

        when(placeRepositories.findAllByStatus(any(), any(Pageable.class))).thenReturn(places);
        when(placeMapper.toDTOsResponse(any())).thenReturn(List.of(placeDTOResponse));

        List<PlaceDTOResponse> result = moderatorService.showPlaceByStatus(status, filterRequest);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(placeRepositories, times(1)).findAllByStatus(any(), any(Pageable.class));
    }

    @Test
    void testUpdateArticle() {
        Integer articleId = 1;
        ArticleDTO articleDTO = new ArticleDTO();
        Article article = new Article();

        when(articleRepositories.findById(articleId)).thenReturn(Optional.of(article));

        moderatorService.updateArticle(articleDTO, articleId);

        verify(articleMapper, times(1)).updateArticleFromDTO(article, articleDTO);
        verify(articleRepositories, times(1)).save(article);
    }

    @Test
    void testUpdatePlace() {
        Integer placeId = 1;
        PlaceDTO placeDTO = new PlaceDTO();
        Place place = new Place();

        when(placeRepositories.findById(placeId)).thenReturn(Optional.of(place));

        moderatorService.updatePlace(placeDTO, placeId);

        verify(placeMapper, times(1)).updatePlaceFromDTO(placeDTO, place);
        verify(placeRepositories, times(1)).save(place);
    }

    @Test
    void testUpdateEntityStatusArticleNotApproved() {
        Integer articleId = 1;
        RedactorDTO redactorDTO = new RedactorDTO();
        redactorDTO.setStatus("неодобренно");
        EntityType entityType = EntityType.ARTICLE;
        Mistake mistake = new Mistake();

        when(mistakeMapper.toEntity(any())).thenReturn(mistake);
        when(articleRepositories.findById(articleId)).thenReturn(Optional.of(new Article()));

        moderatorService.updateEntityStatus(redactorDTO, articleId, entityType);

        verify(mistakeRepositories, times(1)).save(mistake);
        verify(articleRepositories, times(1)).save(any(Article.class));
    }

    @Test
    void testUpdateEntityStatusPlaceNotApproved() {
        Integer placeId = 1;
        RedactorDTO redactorDTO = new RedactorDTO();
        redactorDTO.setStatus("неодобренно");
        EntityType entityType = EntityType.PLACE;
        Mistake mistake = new Mistake();

        when(mistakeMapper.toEntity(any())).thenReturn(mistake);
        when(placeRepositories.findById(placeId)).thenReturn(Optional.of(new Place()));

        moderatorService.updateEntityStatus(redactorDTO, placeId, entityType);

        verify(mistakeRepositories, times(1)).save(mistake);
        verify(placeRepositories, times(1)).save(any(Place.class));
    }

    @Test
    void testUpdateEntityStatusArticleApproved() {
        Integer articleId = 1;
        RedactorDTO redactorDTO = new RedactorDTO();
        redactorDTO.setStatus("одобренно");
        EntityType entityType = EntityType.ARTICLE;

        when(articleRepositories.findById(articleId)).thenReturn(Optional.of(new Article()));

        moderatorService.updateEntityStatus(redactorDTO, articleId, entityType);

        verify(articleRepositories, times(1)).save(any(Article.class));
    }

    @Test
    void testUpdateEntityStatusPlaceApproved() {
        Integer placeId = 1;
        RedactorDTO redactorDTO = new RedactorDTO();
        redactorDTO.setStatus("одобренно");
        EntityType entityType = EntityType.PLACE;

        when(placeRepositories.findById(placeId)).thenReturn(Optional.of(new Place()));

        moderatorService.updateEntityStatus(redactorDTO, placeId, entityType);

        verify(placeRepositories, times(1)).save(any(Place.class));
    }
}
