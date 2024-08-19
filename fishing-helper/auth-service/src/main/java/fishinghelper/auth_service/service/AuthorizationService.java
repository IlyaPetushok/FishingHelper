package fishinghelper.auth_service.service;

import fishinghelper.auth_service.dto.AuthenticationDTOResponse;
import fishinghelper.auth_service.dto.TokenRequest;
import fishinghelper.auth_service.dto.UserDTORequestAuthorization;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService {
    AuthenticationDTOResponse userAuthorization(UserDTORequestAuthorization userDTORequestAuthorization) ;
    void checkExpireToken(TokenRequest tokenRequest);
    ResponseEntity<?> refreshToken(TokenRequest tokenRequest);
    void requestUpdatePassword(Integer id);
    void updatePassword(HttpServletRequest request);
}
