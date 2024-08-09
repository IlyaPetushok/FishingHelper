package fishinghelper.photo_service.advice;

import fishinghelper.photo_service.exception.CustomResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class PhotoServiceGlobalExceptionHandler {

    @ExceptionHandler(value = CustomResponseException.class)
    public ResponseEntity<?> handlerGenericException(CustomResponseException customResponseException) {
        log.error(customResponseException.toString());
        return ResponseEntity
                .status(customResponseException.getHttpStatus())
                .body(customResponseException.toString());
    }

}
