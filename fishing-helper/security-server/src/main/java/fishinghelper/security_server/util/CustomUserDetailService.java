package fishinghelper.security_server.util;

import fishinghelper.common_module.dao.RoleRepositories;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.Role;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.security_server.exception.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService {
    private final UserRepositories userRepositories;
    private final RoleRepositories roleRepositories;

    @Autowired
    public CustomUserDetailService(UserRepositories userRepositories, RoleRepositories roleRepositories) {
        this.userRepositories = userRepositories;
        this.roleRepositories = roleRepositories;
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
    public List<GrantedAuthority> loadUserByUsername(String login, List<String> roleNamesRealm) throws UsernameNotFoundException {
        User user = userRepositories.findUserByLogin(login);

        List<Role> rolesRealm = new ArrayList<>();
        List<Role> roles = roleRepositories.findAll();

        if (user == null) {
            throw new UsernameNotFoundException("doesnt found user by token");
        }

        for (String roleRealm : roleNamesRealm) {
            rolesRealm.add(roles.stream()
                    .filter(role -> role.getName().equals(roleRealm))
                    .findAny()
                    .orElseThrow(() -> new CustomResponseException(HttpStatus.CONFLICT,"dont found role for realm")));
        }
        return CustomUserDetail.fromUserEntity(user.getPrivileges(), rolesRealm);
    }
}