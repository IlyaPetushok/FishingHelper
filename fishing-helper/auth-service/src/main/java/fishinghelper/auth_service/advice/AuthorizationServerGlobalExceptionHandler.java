package fishinghelper.auth_service.advice;


import fishinghelper.auth_service.exception.CustomResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class AuthorizationServerGlobalExceptionHandler {

    @ExceptionHandler(value = CustomResponseException.class)
    public ResponseEntity<?> handlerGenericException(CustomResponseException customResponseException){
        log.error(customResponseException.toString());
        return ResponseEntity
                .status(customResponseException.getHttpStatus())
                .body(customResponseException.toString()) ;
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<?> handlerGenericException(SQLException exceptionResponse){
        log.error(exceptionResponse.toString());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse.toString()) ;
    }
}
