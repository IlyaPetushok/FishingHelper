package fishinghelper.security_server.util;

import fishinghelper.common_module.entity.user.Privileges;
import fishinghelper.common_module.entity.user.Role;
import fishinghelper.common_module.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {
    private String login;
    private String password;
    public Collection<? extends GrantedAuthority> grantedAuthorities;

    public static CustomUserDetail fromUserEntity(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        CustomUserDetail c = new CustomUserDetail();

        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            for (Privileges privilege : role.getPrivileges()) {
                if (user.getPrivileges().stream().noneMatch(constr-> constr.getName().equals(privilege.getName()))) {
                    authorities.add(new SimpleGrantedAuthority(privilege.getName()));
                }
            }
        }

        c.grantedAuthorities = authorities;
        c.login = user.getLogin();
        c.password = user.getPassword();
        return c;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }
}
