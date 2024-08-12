package fishinghelper.auth_service.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponseException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            Map<String, Object> exceptionDetails = new HashMap<>();
            exceptionDetails.put("type", getClass().getSimpleName());
            exceptionDetails.put("message", getMessage());
            exceptionDetails.put("httpStatus", getHttpStatus().toString());

            return objectMapper.writeValueAsString(exceptionDetails);
        } catch (JsonProcessingException e) {
            return "{\"error\": \"Failed to serialize exception details.\"}";
        }

    }
}
