package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.common.Article;
import fishinghelper.common_module.entity.common.Photo;
import fishinghelper.common_module.entity.common.Tags;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.user_service.dto.ArticleDTORequest;
import fishinghelper.user_service.dto.ArticleDTOResponse;
import fishinghelper.user_service.dto.PhotoDTORequest;
import fishinghelper.user_service.dto.TagDTO;
import fishinghelper.user_service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-29T22:03:40+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public Article toEntity(ArticleDTORequest articleDTORequest) {
        if ( articleDTORequest == null ) {
            return null;
        }

        Article article = new Article();

        article.setName( articleDTORequest.getName() );
        article.setDescription( articleDTORequest.getDescription() );
        article.setImportance( articleDTORequest.isImportance() );
        article.setTagsList( tagDTOListToTagsList( articleDTORequest.getTagsList() ) );
        article.setPhotos( photoDTORequestListToPhotoList( articleDTORequest.getPhotos() ) );

        return article;
    }

    @Override
    public List<ArticleDTOResponse> toDTOs(List<Article> articles) {
        if ( articles == null ) {
            return null;
        }

        List<ArticleDTOResponse> list = new ArrayList<ArticleDTOResponse>( articles.size() );
        for ( Article article : articles ) {
            list.add( toDTO( article ) );
        }

        return list;
    }

    @Override
    public ArticleDTOResponse toDTO(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleDTOResponse articleDTOResponse = new ArticleDTOResponse();

        articleDTOResponse.setName( article.getName() );
        articleDTOResponse.setDescription( article.getDescription() );
        articleDTOResponse.setUser( userToUserDTO( article.getUser() ) );
        articleDTOResponse.setImportance( article.isImportance() );

        return articleDTOResponse;
    }

    protected Tags tagDTOToTags(TagDTO tagDTO) {
        if ( tagDTO == null ) {
            return null;
        }

        Tags tags = new Tags();

        tags.setName( tagDTO.getName() );

        return tags;
    }

    protected List<Tags> tagDTOListToTagsList(List<TagDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Tags> list1 = new ArrayList<Tags>( list.size() );
        for ( TagDTO tagDTO : list ) {
            list1.add( tagDTOToTags( tagDTO ) );
        }

        return list1;
    }

    protected Photo photoDTORequestToPhoto(PhotoDTORequest photoDTORequest) {
        if ( photoDTORequest == null ) {
            return null;
        }

        Photo photo = new Photo();

        photo.setPhotoPath( photoDTORequest.getPhotoPath() );

        return photo;
    }

    protected List<Photo> photoDTORequestListToPhotoList(List<PhotoDTORequest> list) {
        if ( list == null ) {
            return null;
        }

        List<Photo> list1 = new ArrayList<Photo>( list.size() );
        for ( PhotoDTORequest photoDTORequest : list ) {
            list1.add( photoDTORequestToPhoto( photoDTORequest ) );
        }

        return list1;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );

        return userDTO;
    }
}
