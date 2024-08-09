package fishinghelper.photo_service.exception;

import org.springframework.http.HttpStatus;

public class FileAlreadyExistException extends CustomResponseException{
    public FileAlreadyExistException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
