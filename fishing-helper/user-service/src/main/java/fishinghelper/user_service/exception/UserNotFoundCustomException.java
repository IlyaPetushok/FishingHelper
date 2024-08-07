package fishinghelper.user_service.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundCustomException extends CustomResponseException {
    public UserNotFoundCustomException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
