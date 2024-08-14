package fishinghelper.auth_service.service;

import fishinghelper.auth_service.dto.UserDTORequestRegistration;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {
    void createUser(UserDTORequestRegistration userDTORequestRegistration);

    void updateStatusConfirmEmail(String email);
}
