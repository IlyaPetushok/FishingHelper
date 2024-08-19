package fishinghelper.user_service.service;

import fishinghelper.common_module.filter.FilterRequest;
import fishinghelper.common_module.filter.FilterResponse;
import fishinghelper.user_service.dto.ArticleDTORequest;
import fishinghelper.user_service.dto.ArticleDTOResponse;
import fishinghelper.user_service.dto.TagsDTO;
import fishinghelper.user_service.dto.filter.ArticleDTOFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ArticleService {
    void createArticle(ArticleDTORequest articleDTORequest);

    List<ArticleDTOResponse> findArticle(TagsDTO tagsDTO);

    ArticleDTOResponse findArticleById(Integer id);

    FilterResponse<ArticleDTOResponse> showAllArticle(FilterRequest filterRequest);

    FilterResponse<ArticleDTOResponse> showAllArticleFilter(ArticleDTOFilter articleDTOFilter);
}
