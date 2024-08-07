package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.RoleDTO;
import fishinghelper.common_module.entity.user.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    List<Role> toEntities(List<RoleDTO> roleDTOList);
}
