package fishinghelper.auth_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOResponseAuthorization {
    private Integer id;
    private String name;
    private String login;
    private String password;
    private List<RoleDTO> roles;
}
