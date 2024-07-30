package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.common.Article;
import fishinghelper.user_service.dto.ArticleDTORequest;
import fishinghelper.user_service.dto.ArticleDTOResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface  ArticleMapper {
    Article toEntity(ArticleDTORequest articleDTORequest);

    List<ArticleDTOResponse> toDTOs(List<Article> articles);

    ArticleDTOResponse toDTO(Article article);
}

