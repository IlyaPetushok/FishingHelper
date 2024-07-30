package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.ArticleDTO;
import fishinghelper.admin_service.dto.ArticleDTOResponse;
import fishinghelper.common_module.entity.common.Article;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-29T22:19:54+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public List<ArticleDTOResponse> toDTOsResponse(List<Article> articles) {
        if ( articles == null ) {
            return null;
        }

        List<ArticleDTOResponse> list = new ArrayList<ArticleDTOResponse>( articles.size() );
        for ( Article article : articles ) {
            list.add( articleToArticleDTOResponse( article ) );
        }

        return list;
    }

    @Override
    public Article toEntity(ArticleDTO articleDTO) {
        if ( articleDTO == null ) {
            return null;
        }

        Article article = new Article();

        article.setId( articleDTO.getId() );
        article.setName( articleDTO.getName() );
        article.setDescription( articleDTO.getDescription() );
        article.setImportance( articleDTO.isImportance() );
        article.setStatus( articleDTO.getStatus() );

        return article;
    }

    @Override
    public ArticleDTO toDTO(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleDTO articleDTO = new ArticleDTO();

        articleDTO.setId( article.getId() );
        articleDTO.setName( article.getName() );
        articleDTO.setDescription( article.getDescription() );
        articleDTO.setImportance( article.isImportance() );
        articleDTO.setStatus( article.getStatus() );

        return articleDTO;
    }

    @Override
    public void updateArticleFromDTO(Article article, ArticleDTO articleDTO) {
        if ( articleDTO == null ) {
            return;
        }

        if ( articleDTO.getId() != null ) {
            article.setId( articleDTO.getId() );
        }
        if ( articleDTO.getName() != null ) {
            article.setName( articleDTO.getName() );
        }
        if ( articleDTO.getDescription() != null ) {
            article.setDescription( articleDTO.getDescription() );
        }
        article.setImportance( articleDTO.isImportance() );
        if ( articleDTO.getStatus() != null ) {
            article.setStatus( articleDTO.getStatus() );
        }
    }

    protected ArticleDTOResponse articleToArticleDTOResponse(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleDTOResponse articleDTOResponse = new ArticleDTOResponse();

        articleDTOResponse.setName( article.getName() );
        articleDTOResponse.setDescription( article.getDescription() );
        articleDTOResponse.setImportance( article.isImportance() );

        return articleDTOResponse;
    }
}
