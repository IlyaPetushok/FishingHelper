package fishinghelper.admin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponseFindRole {
    private String login;
    private String mail;
    private List<RoleDTO> roles;
}
