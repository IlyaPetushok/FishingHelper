package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.common.Comment;
import fishinghelper.common_module.entity.common.Photo;
import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.common_module.entity.place.Owner;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.common_module.entity.place.TypePlace;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.user_service.dto.CommentDTO;
import fishinghelper.user_service.dto.FishDTO;
import fishinghelper.user_service.dto.OwnerDTO;
import fishinghelper.user_service.dto.PhotoDTORequest;
import fishinghelper.user_service.dto.PhotoDTOResponse;
import fishinghelper.user_service.dto.PlaceDTORequest;
import fishinghelper.user_service.dto.PlaceDTOResponse;
import fishinghelper.user_service.dto.PlaceDTOResponseAll;
import fishinghelper.user_service.dto.TypePlaceDTO;
import fishinghelper.user_service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-29T22:03:39+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17 (Oracle Corporation)"
)
@Component
public class PlaceMapperImpl implements PlaceMapper {

    @Override
    public List<PlaceDTOResponseAll> toDTOs(List<Place> places) {
        if ( places == null ) {
            return null;
        }

        List<PlaceDTOResponseAll> list = new ArrayList<PlaceDTOResponseAll>( places.size() );
        for ( Place place : places ) {
            list.add( toDTOResponseAll( place ) );
        }

        return list;
    }

    @Override
    public PlaceDTOResponse toDTO(Place place) {
        if ( place == null ) {
            return null;
        }

        PlaceDTOResponse placeDTOResponse = new PlaceDTOResponse();

        placeDTOResponse.setId( place.getId() );
        placeDTOResponse.setName( place.getName() );
        placeDTOResponse.setCoordinates( place.getCoordinates() );
        placeDTOResponse.setDescription( place.getDescription() );
        placeDTOResponse.setRequirePayment( place.isRequirePayment() );
        placeDTOResponse.setOwner( ownerToOwnerDTO( place.getOwner() ) );
        placeDTOResponse.setTypePlace( typePlaceToTypePlaceDTO( place.getTypePlace() ) );
        placeDTOResponse.setPhotos( photoListToPhotoDTOResponseList( place.getPhotos() ) );
        placeDTOResponse.setFish( fishListToFishDTOList( place.getFish() ) );
        placeDTOResponse.setComments( commentListToCommentDTOList( place.getComments() ) );

        return placeDTOResponse;
    }

    @Override
    public PlaceDTOResponseAll toDTOResponseAll(Place place) {
        if ( place == null ) {
            return null;
        }

        PlaceDTOResponseAll placeDTOResponseAll = new PlaceDTOResponseAll();

        placeDTOResponseAll.setId( place.getId() );
        placeDTOResponseAll.setName( place.getName() );
        placeDTOResponseAll.setCoordinates( place.getCoordinates() );
        placeDTOResponseAll.setRequirePayment( place.isRequirePayment() );
        placeDTOResponseAll.setTypePlace( typePlaceToTypePlaceDTO( place.getTypePlace() ) );
        placeDTOResponseAll.setPhotos( photoListToPhotoDTOResponseList( place.getPhotos() ) );

        return placeDTOResponseAll;
    }

    @Override
    public Place toEntity(PlaceDTORequest placeDTORequest) {
        if ( placeDTORequest == null ) {
            return null;
        }

        Place place = new Place();

        place.setFish( mapToFishList( placeDTORequest.getFish() ) );
        place.setName( placeDTORequest.getName() );
        place.setCoordinates( placeDTORequest.getCoordinates() );
        place.setRequirePayment( placeDTORequest.isRequirePayment() );
        place.setDescription( placeDTORequest.getDescription() );
        place.setTypePlace( typePlaceDTOToTypePlace( placeDTORequest.getTypePlace() ) );
        place.setOwner( ownerDTOToOwner( placeDTORequest.getOwner() ) );
        place.setPhotos( photoDTORequestListToPhotoList( placeDTORequest.getPhotos() ) );

        return place;
    }

    protected OwnerDTO ownerToOwnerDTO(Owner owner) {
        if ( owner == null ) {
            return null;
        }

        OwnerDTO ownerDTO = new OwnerDTO();

        ownerDTO.setId( owner.getId() );
        ownerDTO.setName( owner.getName() );
        ownerDTO.setNumber( owner.getNumber() );

        return ownerDTO;
    }

    protected TypePlaceDTO typePlaceToTypePlaceDTO(TypePlace typePlace) {
        if ( typePlace == null ) {
            return null;
        }

        TypePlaceDTO typePlaceDTO = new TypePlaceDTO();

        typePlaceDTO.setId( typePlace.getId() );
        typePlaceDTO.setName( typePlace.getName() );

        return typePlaceDTO;
    }

    protected PhotoDTOResponse photoToPhotoDTOResponse(Photo photo) {
        if ( photo == null ) {
            return null;
        }

        PhotoDTOResponse photoDTOResponse = new PhotoDTOResponse();

        photoDTOResponse.setPhotoPath( photo.getPhotoPath() );

        return photoDTOResponse;
    }

    protected List<PhotoDTOResponse> photoListToPhotoDTOResponseList(List<Photo> list) {
        if ( list == null ) {
            return null;
        }

        List<PhotoDTOResponse> list1 = new ArrayList<PhotoDTOResponse>( list.size() );
        for ( Photo photo : list ) {
            list1.add( photoToPhotoDTOResponse( photo ) );
        }

        return list1;
    }

    protected FishDTO fishToFishDTO(Fish fish) {
        if ( fish == null ) {
            return null;
        }

        FishDTO fishDTO = new FishDTO();

        fishDTO.setId( fish.getId() );
        fishDTO.setName( fish.getName() );

        return fishDTO;
    }

    protected List<FishDTO> fishListToFishDTOList(List<Fish> list) {
        if ( list == null ) {
            return null;
        }

        List<FishDTO> list1 = new ArrayList<FishDTO>( list.size() );
        for ( Fish fish : list ) {
            list1.add( fishToFishDTO( fish ) );
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

    protected CommentDTO commentToCommentDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( comment.getId() );
        commentDTO.setText( comment.getText() );
        commentDTO.setGrade( comment.getGrade() );
        commentDTO.setUser( userToUserDTO( comment.getUser() ) );

        return commentDTO;
    }

    protected List<CommentDTO> commentListToCommentDTOList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDTO> list1 = new ArrayList<CommentDTO>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentToCommentDTO( comment ) );
        }

        return list1;
    }

    protected TypePlace typePlaceDTOToTypePlace(TypePlaceDTO typePlaceDTO) {
        if ( typePlaceDTO == null ) {
            return null;
        }

        TypePlace typePlace = new TypePlace();

        typePlace.setId( typePlaceDTO.getId() );
        typePlace.setName( typePlaceDTO.getName() );

        return typePlace;
    }

    protected Owner ownerDTOToOwner(OwnerDTO ownerDTO) {
        if ( ownerDTO == null ) {
            return null;
        }

        Owner owner = new Owner();

        owner.setId( ownerDTO.getId() );
        owner.setName( ownerDTO.getName() );
        owner.setNumber( ownerDTO.getNumber() );

        return owner;
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
}
