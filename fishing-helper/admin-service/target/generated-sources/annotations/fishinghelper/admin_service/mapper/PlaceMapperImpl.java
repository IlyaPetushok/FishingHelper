package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.PlaceDTO;
import fishinghelper.admin_service.dto.PlaceDTOResponse;
import fishinghelper.common_module.entity.place.Place;
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
public class PlaceMapperImpl implements PlaceMapper {

    @Override
    public List<PlaceDTOResponse> toDTOsResponse(List<Place> places) {
        if ( places == null ) {
            return null;
        }

        List<PlaceDTOResponse> list = new ArrayList<PlaceDTOResponse>( places.size() );
        for ( Place place : places ) {
            list.add( placeToPlaceDTOResponse( place ) );
        }

        return list;
    }

    @Override
    public Place toEntity(PlaceDTO placeDTO) {
        if ( placeDTO == null ) {
            return null;
        }

        Place place = new Place();

        place.setId( placeDTO.getId() );
        place.setName( placeDTO.getName() );
        place.setCoordinates( placeDTO.getCoordinates() );
        place.setRequirePayment( placeDTO.isRequirePayment() );
        place.setDescription( placeDTO.getDescription() );
        place.setRating( placeDTO.getRating() );
        place.setStatus( placeDTO.getStatus() );

        return place;
    }

    @Override
    public void updatePlaceFromDTO(PlaceDTO placeDTO, Place place) {
        if ( placeDTO == null ) {
            return;
        }

        if ( placeDTO.getId() != null ) {
            place.setId( placeDTO.getId() );
        }
        if ( placeDTO.getName() != null ) {
            place.setName( placeDTO.getName() );
        }
        if ( placeDTO.getCoordinates() != null ) {
            place.setCoordinates( placeDTO.getCoordinates() );
        }
        place.setRequirePayment( placeDTO.isRequirePayment() );
        if ( placeDTO.getDescription() != null ) {
            place.setDescription( placeDTO.getDescription() );
        }
        if ( placeDTO.getRating() != null ) {
            place.setRating( placeDTO.getRating() );
        }
        if ( placeDTO.getStatus() != null ) {
            place.setStatus( placeDTO.getStatus() );
        }
    }

    @Override
    public PlaceDTO toDTO(Place place) {
        if ( place == null ) {
            return null;
        }

        PlaceDTO placeDTO = new PlaceDTO();

        placeDTO.setId( place.getId() );
        placeDTO.setName( place.getName() );
        placeDTO.setCoordinates( place.getCoordinates() );
        placeDTO.setRequirePayment( place.isRequirePayment() );
        placeDTO.setDescription( place.getDescription() );
        placeDTO.setRating( place.getRating() );
        placeDTO.setStatus( place.getStatus() );

        return placeDTO;
    }

    protected PlaceDTOResponse placeToPlaceDTOResponse(Place place) {
        if ( place == null ) {
            return null;
        }

        PlaceDTOResponse placeDTOResponse = new PlaceDTOResponse();

        placeDTOResponse.setName( place.getName() );
        placeDTOResponse.setCoordinates( place.getCoordinates() );
        placeDTOResponse.setRequirePayment( place.isRequirePayment() );
        placeDTOResponse.setDescription( place.getDescription() );

        return placeDTOResponse;
    }
}
