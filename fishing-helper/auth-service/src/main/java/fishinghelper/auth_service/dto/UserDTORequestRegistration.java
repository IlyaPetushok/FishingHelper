package fishinghelper.auth_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTORequestRegistration {
    private String name;
    private String login;
    private String password;
    private String mail;
}
