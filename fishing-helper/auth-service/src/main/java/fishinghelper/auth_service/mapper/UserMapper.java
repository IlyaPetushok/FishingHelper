package fishinghelper.auth_service.mapper;

import fishinghelper.auth_service.dto.UserDTORequestRegistration;
import fishinghelper.common_module.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTORequestRegistration userDTORequestRegistration);
}
