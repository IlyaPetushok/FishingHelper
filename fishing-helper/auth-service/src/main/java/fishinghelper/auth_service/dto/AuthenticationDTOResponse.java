package fishinghelper.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTOResponse {
    private Object response;
    private UserDTOResponseAuthorization userDTO;
}
