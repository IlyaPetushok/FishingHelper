package fishinghelper.photo_service.exception;

import org.springframework.http.HttpStatus;

public class FileNotUploadingException extends CustomResponseException{
    public FileNotUploadingException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
