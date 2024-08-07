package fishinghelper.auth_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponseRegistration {
    private String name;
    private  String login;
    private boolean mailStatus;
}
