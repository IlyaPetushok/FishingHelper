package fishinghelper.photo_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.photo_service.dto.PhotoResponseDTO;
import fishinghelper.photo_service.exception.*;
import fishinghelper.photo_service.service.FileSenderYandexCloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.*;

@Slf4j
@Service
public class FileSenderYandexCloudServiceImpl implements FileSenderYandexCloudService {
    @Value("${directory.name.yandex.cloud}")
    private String directoryName;

    @Value("${public.url.yandex.cloud}")
    private String publicUrl;

    @Value("${oauth.token.yandex.cloud}")
    private String token;

    @Value("${upload.url.yandex.cloud}")
    private String uploadUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public FileSenderYandexCloudServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * Uploads multiple files to Yandex Disk.
     *
     * @param multipartFiles Array of files to be uploaded.
     * @return List of PhotoResponseDTO containing the names and public links of uploaded files.
     * @throws CustomResponseException if an error occurs during file upload.
     */
    @Override
    public List<PhotoResponseDTO> uploadFiles(MultipartFile[] multipartFiles) {
        List<PhotoResponseDTO> photoResponseDTOS = new ArrayList<>();

        Arrays.asList(multipartFiles).forEach(file -> {
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null) {
                throw new CustomResponseException(HttpStatus.NO_CONTENT, "Original file name is null");
            }
            fileExists(originalFileName);
            try {
                photoResponseDTOS.add(uploadFile(file, originalFileName));
            } catch (Exception e) {
                log.error("Error occurred while uploading. Error: ", e);
                throw new FileNotUploadingException(HttpStatus.BAD_REQUEST, "Error occurred while uploading");
            }
        });

        return photoResponseDTOS;
    }

    /**
     * Uploads a single file to Yandex Disk.
     *
     * @param file             The file to be uploaded.
     * @param originalFileName The original name of the file.
     * @return PhotoResponseDTO containing the name and public link of the uploaded file.
     * @throws IOException if an I/O error occurs during file upload.
     */
    private PhotoResponseDTO uploadFile(MultipartFile file, String originalFileName) throws IOException {
        String uploadLink = getUploadLink(originalFileName);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "OAuth " + token);

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> response = restTemplate.exchange(uploadLink, HttpMethod.PUT, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            String publicLink = createPublicLink(originalFileName);
            return new PhotoResponseDTO(originalFileName, publicLink);
        } else {
            throw new FileNotUploadingException(HttpStatus.BAD_REQUEST, "Failed to upload file");
        }
    }


    /**
     * Gets the upload link for a file.
     *
     * @param originalFileName The original name of the file.
     * @return The URL to upload the file.
     * @throws JsonProcessingException if an error occurs while processing JSON.
     */
    private String getUploadLink(String originalFileName) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "OAuth " + token);

        Map<String, String> params = new HashMap<>();
        params.put("path", directoryName + "/" + originalFileName);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uploadUrl + "?path={path}&overwrite=true",
                HttpMethod.GET,
                requestEntity,
                String.class,
                params
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return objectMapper.readTree(response.getBody()).get("href").asText();
        } else {
            throw new FileGetUploadingLinkException(HttpStatus.BAD_REQUEST, "Failed to get upload link.");
        }
    }


    /**
     * Creates a public link for a file on Yandex Disk.
     *
     * @param filePath The path of the file on Yandex Disk.
     * @return The public URL of the file.
     * @throws JsonProcessingException if an error occurs while processing JSON.
     */
    private String createPublicLink(String filePath) throws JsonProcessingException {
        String publicLinkApiUrl = publicUrl + "?path=" + directoryName + "/" + filePath;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "OAuth " + token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(publicLinkApiUrl, HttpMethod.PUT, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String urlForGetPathFile = objectMapper.readTree(response.getBody()).get("href").asText();
            log.info(urlForGetPathFile);
            HttpClient client = HttpClient.newHttpClient();
            java.net.http.HttpRequest httpRequest = java.net.http.HttpRequest.newBuilder()
                    .uri(URI.create(urlForGetPathFile))
                    .header("Authorization", "OAuth " + token)
                    .GET()
                    .build();
            try {
                return parseResponseForGetPublicLink(client, httpRequest);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            throw new FileCreatePublicLinkException(HttpStatus.BAD_REQUEST, "Failed to create public link");
        }
        return null;
    }

    /**
     * Parses the response to extract the public link for the file.
     *
     * @param client      The HttpClient used for sending requests.
     * @param httpRequest The HttpRequest to be sent.
     * @return The public URL of the file.
     * @throws IOException          if an I/O error occurs while processing the response.
     * @throws InterruptedException if the request is interrupted.
     */
    private String parseResponseForGetPublicLink(HttpClient client, java.net.http.HttpRequest httpRequest) throws IOException, InterruptedException {
        HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        JsonNode rootNode = objectMapper.readTree(httpResponse.body());

        JsonNode sizesFormatFile = rootNode.get("sizes").get(0);

        return sizesFormatFile.get("url").asText();//original format
    }

    /**
     * @param filePath The path to the file on Yandex.Disk (e.g., "folder/subfolder/file.txt").
     *
     * @throws FileNotUploadingException If the file already exists.
     * @throws CustomResponseException If an error occurs other than file not found.
     */
    public void fileExists(String filePath) {
        String url = "https://cloud-api.yandex.net/v1/disk/resources?path="+directoryName+"/" + filePath;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "OAuth " + token);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            if (restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class).getStatusCode() == HttpStatus.OK) {
                throw new FileNotUploadingException(HttpStatus.CONFLICT, "This file already exists");
            }
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw new CustomResponseException(HttpStatus.BAD_REQUEST, exception.getMessage());
            }
            log.info("Cloud dont have this file");
        }
    }
}