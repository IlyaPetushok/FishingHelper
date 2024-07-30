package fishinghelper.admin_service.dto;

import fishinghelper.common_module.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponse {
    private String name;
    private String login;
    private String password;
    private String mail;
    private boolean mailStatus;
    private Date dateRegistration;
    private List<Role> roles;
}
