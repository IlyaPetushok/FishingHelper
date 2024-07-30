package fishinghelper.security_server.exception;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends CustomResponseException{
    public TokenExpiredException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
