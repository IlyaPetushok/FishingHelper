package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.PrivilegesDTO;
import fishinghelper.admin_service.dto.RoleDTO;
import fishinghelper.admin_service.dto.UserDTOResponse;
import fishinghelper.admin_service.dto.UserDTOResponseFindRole;
import fishinghelper.common_module.entity.user.Privileges;
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
    @Mapping(target = "constraints",source = "user.privileges",qualifiedByName = "mapToPrivileges")
    List<UserDTOResponseFindRole> toDTOSResponse(List<User> userList);

    @Mapping(target = "constraints", source = "user.privileges")
    UserDTOResponseFindRole toDTOResponseFindRole(User user);

    @Named("mapToPrivileges")
    default List<PrivilegesDTO> mapToPrivileges(List<Privileges> privileges){
        return privileges.stream()
                .map(privil ->  new PrivilegesDTO(privil.getName()))
                .collect(Collectors.toList());
    }
 }
