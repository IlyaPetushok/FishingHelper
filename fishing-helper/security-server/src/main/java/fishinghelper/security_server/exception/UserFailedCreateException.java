package fishinghelper.security_server.exception;

import org.springframework.http.HttpStatus;

public class UserFailedCreateException extends CustomResponseException{
    public UserFailedCreateException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
