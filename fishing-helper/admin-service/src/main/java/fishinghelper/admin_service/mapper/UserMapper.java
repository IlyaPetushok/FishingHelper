package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.UserDTOResponse;
import fishinghelper.common_module.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTOResponse toDTOResponse(User user);
}
