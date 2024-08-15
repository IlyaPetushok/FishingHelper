package fishinghelper.security_server.util;

import fishinghelper.common_module.dao.RoleRepositories;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.Privileges;
import fishinghelper.common_module.entity.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public List<GrantedAuthority> loadUserByUsername(String login, List<String> roleNamesRealm) {
        List<Privileges> privileges = userRepositories.findUserByLogin(login).getPrivileges();
        List<Role> rolesRealm = new ArrayList<>();
        List<Role> roles = roleRepositories.findAll();

        for (String roleRealm : roleNamesRealm) {
            rolesRealm.add(roles.stream()
                    .filter(role -> role.getName().equals(roleRealm))
                    .findAny()
                    .orElse(null));
        }
        return CustomUserDetail.fromUserEntity(privileges, rolesRealm);
    }
}