package fishinghelper.auth_service.exception;

import org.springframework.http.HttpStatus;

public class InvalidDataException extends CustomResponseException {
    public InvalidDataException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
