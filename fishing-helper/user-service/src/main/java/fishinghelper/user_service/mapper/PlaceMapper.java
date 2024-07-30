package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.common_module.entity.place.Place;
import fishinghelper.user_service.dto.PlaceDTORequest;
import fishinghelper.user_service.dto.PlaceDTOResponse;
import fishinghelper.user_service.dto.PlaceDTOResponseAll;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    List<PlaceDTOResponseAll> toDTOs(List<Place> places);

    PlaceDTOResponse toDTO(Place place);

    PlaceDTOResponseAll toDTOResponseAll(Place place);

    @Mapping(target = "fish ",source = "fish", qualifiedByName = "mapToFishList")
    Place toEntity(PlaceDTORequest placeDTORequest);

    @Named("mapToFishList")
    default List<Fish> mapToFishList(List<Integer> fishId){
        List<Fish> fishList=new ArrayList<>();
        fishId.forEach(fish-> fishList.add(new Fish(fish)));
        return fishList;
    }
}
