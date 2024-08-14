package fishinghelper.user_service.exception;

import org.springframework.http.HttpStatus;

public class WeatherAPIServiceException extends CustomResponseException {
    public WeatherAPIServiceException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
