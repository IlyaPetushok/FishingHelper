package fishinghelper.security_server.exception;

import org.springframework.http.HttpStatus;

public class TokenInvalidException extends CustomResponseException{
    public TokenInvalidException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
