package fishinghelper.admin_service.dto;

import fishinghelper.common_module.entity.user.Privileges;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponseFindRole {
    private String id;
    private String name;
    private String login;
    private String mail;
    private Date dateRegistration;
    private List<PrivilegesDTO> constraints;
}
