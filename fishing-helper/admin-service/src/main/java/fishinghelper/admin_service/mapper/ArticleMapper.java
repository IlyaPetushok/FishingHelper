package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.ArticleDTO;
import fishinghelper.admin_service.dto.ArticleDTOResponse;
import fishinghelper.common_module.entity.common.Article;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ArticleMapper {
    List<ArticleDTOResponse> toDTOsResponse(List<Article> articles);

    Article toEntity(ArticleDTO articleDTO);

    ArticleDTO toDTO(Article article);

    void updateArticleFromDTO(@MappingTarget Article article, ArticleDTO articleDTO);
}
