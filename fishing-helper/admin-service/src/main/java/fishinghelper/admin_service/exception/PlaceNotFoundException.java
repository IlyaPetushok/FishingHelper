package fishinghelper.admin_service.exception;

import org.springframework.http.HttpStatus;

public class PlaceNotFoundException extends CustomResponseException {
    public PlaceNotFoundException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
