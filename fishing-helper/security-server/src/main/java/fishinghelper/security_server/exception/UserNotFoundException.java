package fishinghelper.security_server.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomResponseException {
    public UserNotFoundException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
