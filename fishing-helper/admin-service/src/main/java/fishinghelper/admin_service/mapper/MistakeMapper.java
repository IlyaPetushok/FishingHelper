package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.MistakeDTO;
import fishinghelper.common_module.entity.common.Mistake;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MistakeMapper {

    Mistake toEntity(MistakeDTO mistakeDTO);
}
