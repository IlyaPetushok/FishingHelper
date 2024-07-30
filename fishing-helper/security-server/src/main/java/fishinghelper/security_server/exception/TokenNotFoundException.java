package fishinghelper.security_server.exception;

import org.springframework.http.HttpStatus;

public class TokenNotFoundException extends CustomResponseException{
    public TokenNotFoundException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
