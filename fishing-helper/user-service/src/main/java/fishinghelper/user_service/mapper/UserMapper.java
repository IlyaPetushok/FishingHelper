package fishinghelper.user_service.mapper;

import fishinghelper.common_module.entity.user.User;
import fishinghelper.user_service.dto.UserDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTOResponse toDTO(User user);
}
