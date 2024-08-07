package fishinghelper.security_server.exception;

import lombok.*;
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
