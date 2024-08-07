package fishinghelper.security_server.exception;

import org.springframework.http.HttpStatus;

public class LogoutException extends CustomResponseException{
    public LogoutException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
