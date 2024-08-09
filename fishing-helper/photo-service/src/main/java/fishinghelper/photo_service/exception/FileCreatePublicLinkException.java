package fishinghelper.photo_service.exception;

import org.springframework.http.HttpStatus;

public class FileCreatePublicLinkException extends CustomResponseException{
    public FileCreatePublicLinkException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
