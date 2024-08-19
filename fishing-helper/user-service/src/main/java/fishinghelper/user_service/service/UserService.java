package fishinghelper.user_service.service;

import fishinghelper.common_module.entity.user.User;
import fishinghelper.user_service.dto.UserDTOResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDTOResponse showInfoUser(String login);
}
