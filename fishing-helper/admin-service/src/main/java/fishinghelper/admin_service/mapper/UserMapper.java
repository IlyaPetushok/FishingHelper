package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.RoleDTO;
import fishinghelper.admin_service.dto.UserDTOResponse;
import fishinghelper.admin_service.dto.UserDTOResponseFindRole;
import fishinghelper.common_module.entity.user.Role;
import fishinghelper.common_module.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTOResponse toDTOResponse(User user);
    @Mapping(target = "roles",source = "roles",qualifiedByName = "mapToRole")
    List<UserDTOResponseFindRole> toDTOSResponse(List<User> userList);

    @Named("mapToRole")
    default List<RoleDTO> mapToRole(List<Role> role){
        return role.stream()
                .map(rol ->  new RoleDTO(rol.getName()))
                .collect(Collectors.toList());
    }
 }
