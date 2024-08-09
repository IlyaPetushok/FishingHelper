package fishinghelper.photo_service.controller;


import fishinghelper.photo_service.service.FileSenderYandexCloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PhotoController {

    private final FileSenderYandexCloudService fileSenderYandexCloudService;

    @Autowired
    public PhotoController(FileSenderYandexCloudService fileSenderYandexCloudService) {
        this.fileSenderYandexCloudService = fileSenderYandexCloudService;
    }

    @PostMapping(value = "/sendPhoto",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadFileOnCloud(@RequestParam("files")MultipartFile[] multipartFile){
        return new ResponseEntity<>(fileSenderYandexCloudService.uploadFiles(multipartFile),HttpStatus.OK);
    }
}
