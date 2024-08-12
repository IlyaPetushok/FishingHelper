package fishinghelper.weather_service.advice;

import fishinghelper.weather_service.exception.WeatherInvalidLocationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WeatherExceptionHandler {

    @ExceptionHandler(value = WeatherInvalidLocationException.class)
    public ResponseEntity<?> exceptionHandler(WeatherInvalidLocationException weatherInvalidLocationException){
        return ResponseEntity.status(weatherInvalidLocationException.getHttpStatus())
                .body(weatherInvalidLocationException.getMessage());
    }
}
