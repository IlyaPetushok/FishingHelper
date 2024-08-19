package fishinghelper.user_service.service.impl;

import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.user_service.dto.UserDTOResponse;
import fishinghelper.user_service.mapper.UserMapper;
import fishinghelper.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepositories userRepositories;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepositories userRepositories, UserMapper userMapper) {
        this.userRepositories = userRepositories;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTOResponse showInfoUser(String login) {
        return userMapper.toDTO(userRepositories.findUserByLogin(login));
    }
}
