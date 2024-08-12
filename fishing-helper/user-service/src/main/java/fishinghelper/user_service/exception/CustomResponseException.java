package fishinghelper.user_service.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomResponseException extends RuntimeException{
    private HttpStatus httpStatus;
    private String errorMessage;

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
