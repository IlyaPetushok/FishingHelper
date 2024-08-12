package fishinghelper.auth_service.exception;

import org.springframework.http.HttpStatus;

public class TokenInvalidException extends CustomResponseException{
    public TokenInvalidException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
