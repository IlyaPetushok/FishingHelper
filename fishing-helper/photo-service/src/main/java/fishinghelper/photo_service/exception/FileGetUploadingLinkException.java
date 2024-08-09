package fishinghelper.photo_service.exception;

import org.springframework.http.HttpStatus;

public class FileGetUploadingLinkException extends CustomResponseException{
    public FileGetUploadingLinkException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
