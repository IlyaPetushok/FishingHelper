package fishinghelper.user_service.exception;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomResponseException extends RuntimeException{
    private HttpStatus httpStatus;
    private String errorMessage;
}
