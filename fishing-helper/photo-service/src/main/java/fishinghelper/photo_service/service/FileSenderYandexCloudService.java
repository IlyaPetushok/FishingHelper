package fishinghelper.photo_service.service;

import fishinghelper.photo_service.dto.PhotoResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FileSenderYandexCloudService {
    List<PhotoResponseDTO> uploadFiles(MultipartFile[] multipartFiles);
}
