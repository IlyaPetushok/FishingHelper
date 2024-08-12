package fishinghelper.auth_service.exception;

import org.springframework.http.HttpStatus;

public class RefreshTokenException extends CustomResponseException{
    public RefreshTokenException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
