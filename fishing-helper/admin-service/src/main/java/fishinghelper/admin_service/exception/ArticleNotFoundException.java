package fishinghelper.admin_service.exception;

import org.springframework.http.HttpStatus;

public class ArticleNotFoundException extends CustomResponseException{
    public ArticleNotFoundException(HttpStatus httpStatus, String errorMessage) {
        super(httpStatus, errorMessage);
    }
}
