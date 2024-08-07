package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.fish.Fish;
import fishinghelper.user_service.dto.FishDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FishMapper {
    List<Fish> toEntities(List<FishDTO> fishDTOS);
}
