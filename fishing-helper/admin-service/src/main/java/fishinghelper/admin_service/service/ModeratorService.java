package fishinghelper.admin_service.service;

import fishinghelper.admin_service.dto.*;
import fishinghelper.common_module.filter.FilterRequest;

import java.util.List;

public interface ModeratorService {
    void deletePlace(Integer id);

    void deleteArticle(Integer id);

    List<ArticleDTOResponse> showArticleByStatus(String status, FilterRequest filterRequest);

    List<PlaceDTOResponse> showPlaceByStatus(String status, FilterRequest filterRequest);

    void updateArticle(ArticleDTO articleDTO, Integer id);

    void updatePlace(PlaceDTO placeDTO, Integer id);

    void updateEntityStatus(RedactorDTO redactorDTO, Integer id, EntityType entityType);
}
