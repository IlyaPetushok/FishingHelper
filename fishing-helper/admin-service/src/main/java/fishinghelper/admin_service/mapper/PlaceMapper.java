package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.PlaceDTO;
import fishinghelper.admin_service.dto.PlaceDTOResponse;
import fishinghelper.common_module.entity.place.Place;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring" ,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlaceMapper {
    List<PlaceDTOResponse> toDTOsResponse(List<Place> places);

    Place toEntity(PlaceDTO placeDTO);

    void updatePlaceFromDTO(PlaceDTO placeDTO, @MappingTarget Place place);

    PlaceDTO toDTO(Place place);
}
