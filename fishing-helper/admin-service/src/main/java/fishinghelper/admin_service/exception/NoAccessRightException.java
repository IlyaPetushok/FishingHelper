package fishinghelper.admin_service.exception;

import org.springframework.http.HttpStatus;

public class NoAccessRightException extends CustomResponseException{
    public NoAccessRightException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
