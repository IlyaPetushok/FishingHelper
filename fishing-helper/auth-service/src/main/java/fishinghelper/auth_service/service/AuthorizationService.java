package fishinghelper.auth_service.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.auth_service.dto.TokenRequest;
import fishinghelper.auth_service.dto.UserDTORequestAuthorization;
import fishinghelper.auth_service.exception.UserNotFoundException;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.security_server.service.KeyCloakService;
import fishinghelper.security_server.util.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * Service class for handling user authorization and token management.
 * Provides methods for user authentication, token generation, token refresh, and response handling.
 *
 * @author Ilya Petushok
 */

@Slf4j
@Service
public class AuthorizationService {
    private static final String SUBJECT_UPDATE_PASSWORD = "Request Update Password";

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.url.token}")
    private String urlOpenIdToken;

    private final UserRepositories userRepositories;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;
    private final KeyCloakService keyCloakService;

    @Autowired
    public AuthorizationService(UserRepositories userRepositories, EmailService emailService, ObjectMapper objectMapper, RestTemplate restTemplate, JwtProvider jwtProvider, KeyCloakService keyCloakService) {
        this.userRepositories = userRepositories;
        this.emailService = emailService;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.jwtProvider = jwtProvider;
        this.keyCloakService = keyCloakService;
    }

    public ResponseEntity<?> userAuthorization(UserDTORequestAuthorization userDTORequestAuthorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("client_id", clientId);
        multiValueMap.add("client_secret", clientSecret);
        multiValueMap.add("grant_type", "password");
        multiValueMap.add("username", userDTORequestAuthorization.getLogin());
        multiValueMap.add("password", userDTORequestAuthorization.getPassword());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, headers);

        return restTemplate.postForEntity(urlOpenIdToken, httpEntity, Object.class);
    }

    public void checkExpireToken(TokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("client_id", clientId);
        multiValueMap.add("client_secret", clientSecret);
        multiValueMap.add("token", tokenRequest.getToken());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, headers);
        ResponseEntity<?> response = restTemplate.postForEntity(urlOpenIdToken + "/introspect", httpEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = Objects.requireNonNull(response.getBody()).toString();
            try {
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                boolean isActive = jsonNode.get("active").asBoolean();
                if (isActive) {
                    System.out.println("Token is active.");
                } else {
                    System.out.println("Token is inactive or invalid.");
                    throw new RuntimeException("Token is inactive or invalid.");
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Failed to introspect token: " + response.getStatusCode());
        }
    }


    public ResponseEntity<?> refreshToken(TokenRequest tokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("refresh_token", tokenRequest.getToken());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity(urlOpenIdToken, requestEntity, Object.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        } else {
            throw new RuntimeException("Failed to refresh token: " + responseEntity.getStatusCode());
        }
    }


    /**
     * Requests an update of the user's password based on their ID.
     *
     * @param id the user's identifier
     * @throws UserNotFoundException if the user is not found
     */
    public void requestUpdatePassword(Integer id) {
        User user = userRepositories.findById(id)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "user not found by id:" + id));
        emailService.sendMessage(user, SUBJECT_UPDATE_PASSWORD, readFile());
    }

    /**
     * Reads the content of an HTML file to use in an email message.
     *
     * @return the content of the HTML file as a string
     */
    private String readFile() {
        String line;
        StringBuilder html = new StringBuilder();
        try {
            FileReader fileReader = new FileReader("D:\\intexsoft\\FishingHelper\\FishingHelpers\\auth-service\\src\\main\\resources\\html.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                html.append(line);

            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
        return html.toString();
    }


    public void updatePassword(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String password = request.getParameter("password");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length()).trim();
            String login = jwtProvider.getLoginFromJwt(token);
            keyCloakService.updateUserPassword(login, password);
        } else {
            System.out.println("Authorization header is missing or not in Bearer format");
        }
    }
}
