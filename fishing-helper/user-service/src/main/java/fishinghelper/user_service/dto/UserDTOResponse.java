package fishinghelper.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponse {
    private Integer id;
    private String name;
    private String login;
    private String mail;
    private boolean mailStatus;
    private Date dateRegistration;
}
