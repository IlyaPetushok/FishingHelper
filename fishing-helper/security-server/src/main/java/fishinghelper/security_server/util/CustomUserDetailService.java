package fishinghelper.security_server.util;

import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepositories userRepositories;

    @Autowired
    public CustomUserDetailService(UserRepositories userRepositories) {
        this.userRepositories = userRepositories;
    }

    /**
     * Retrieves the user details (CustomUserDetail) by username (login) from the database.
     * This method is typically used by Spring Security to authenticate and load user details.
     *
     * @param login The login (username) of the user to load.
     * @return A CustomUserDetail object containing user details fetched from the database.
     * @throws UsernameNotFoundException If no user is found with the specified login.
     */

    @Transactional
    @Override
    public CustomUserDetail loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepositories.findUserByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("doesnt found user by login");
        }
        return CustomUserDetail.fromUserEntity(user);
    }
}