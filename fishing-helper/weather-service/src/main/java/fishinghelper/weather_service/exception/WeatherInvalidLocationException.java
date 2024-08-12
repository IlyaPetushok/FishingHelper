package fishinghelper.weather_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInvalidLocationException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;
}
