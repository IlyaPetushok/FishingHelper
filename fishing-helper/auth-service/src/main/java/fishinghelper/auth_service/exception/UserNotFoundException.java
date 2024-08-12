package fishinghelper.auth_service.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomResponseException {
    public UserNotFoundException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
