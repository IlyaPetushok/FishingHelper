package fishinghelper.user_service.exception;

import org.springframework.http.HttpStatus;

public class PlaceNotFoundCustomException extends CustomResponseException {
    public PlaceNotFoundCustomException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
