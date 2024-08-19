package fishinghelper.auth_service.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.auth_service.dto.AuthenticationDTOResponse;
import fishinghelper.auth_service.dto.TokenRequest;
import fishinghelper.auth_service.dto.UserDTORequestAuthorization;
import fishinghelper.auth_service.exception.*;
import fishinghelper.auth_service.mapper.UserMapper;
import fishinghelper.auth_service.service.AuthorizationService;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.notification_service.config.RabbitConfig;
import fishinghelper.notification_service.messaging.producer.RabbitMQProducer;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * Service class for handling user authorization and token management.
 * Provides methods for user authentication, token generation, token refresh, and response handling.
 *
 * @author Ilya Petushok
 */

@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.url.token}")
    private String urlOpenIdToken;

    private final UserMapper userMapper;
    private final UserRepositories userRepositories;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final JwtProvider jwtProvider;
    private final KeyCloakService keyCloakService;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public AuthorizationServiceImpl(UserMapper userMapper, UserRepositories userRepositories, ObjectMapper objectMapper, RestTemplate restTemplate, JwtProvider jwtProvider, KeyCloakService keyCloakService, RabbitMQProducer rabbitMQProducer) {
        this.userMapper = userMapper;
        this.userRepositories = userRepositories;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.jwtProvider = jwtProvider;
        this.keyCloakService = keyCloakService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    /**
     * Authorizes a user by sending a POST request to the authorization server.
     *
     * @param userDTORequestAuthorization the user credentials and other authorization details
     * @return a ResponseEntity containing the result of the authorization request
     */
    @Override
    public AuthenticationDTOResponse userAuthorization(UserDTORequestAuthorization userDTORequestAuthorization) {
        User user=userRepositories.findUserByLogin(userDTORequestAuthorization.getLogin());
        if(Objects.isNull(user)){
            throw new UserNotFoundException(HttpStatus.UNAUTHORIZED,"User was not registered");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();

        multiValueMap.add("client_id", clientId);
        multiValueMap.add("client_secret", clientSecret);
        multiValueMap.add("grant_type", "password");
        multiValueMap.add("username", userDTORequestAuthorization.getLogin());
        multiValueMap.add("password", userDTORequestAuthorization.getPassword());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, headers);

        try {
            ResponseEntity<?> response = restTemplate.postForEntity(urlOpenIdToken, httpEntity, Object.class);
            log.info("Authorization request successful. Response: {}", response);
            return new AuthenticationDTOResponse(response.getBody(),userMapper.toDtoUserResponseAuthorization(user));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Authorization request failed with status code {} and response body: {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new InvalidDataException(HttpStatus.BAD_REQUEST,"Authorization request failed");
        }
    }

    @Override
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
                    log.info("Token is active.");
                } else {
                    log.error("Token is inactive or invalid.");
                    throw new TokenInvalidException(HttpStatus.UNAUTHORIZED,"Token is inactive or invalid.");
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            throw new CustomResponseException(HttpStatus.UNAUTHORIZED,"Failed to introspect token: ");
        }
    }


    /**
     * Refreshes the access token using the provided refresh token.
     *
     * @param tokenRequest the request containing the refresh token
     * @return a ResponseEntity containing the response from the token endpoint
     * @throws RuntimeException if the token refresh fails or if an unexpected error occurs
     */
    @Override
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
            throw new RefreshTokenException(HttpStatus.BAD_REQUEST,"Failed to refresh token");
        }
    }


    /**
     * Requests an update of the user's password based on their ID.
     *
     * @param id the user's identifier
     * @throws UserNotFoundException if the user is not found
     */
    @Override
    public void requestUpdatePassword(Integer id) {
        User user = userRepositories.findById(id)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "user not found by id:" + id));

        rabbitMQProducer.sendMessageQueue(user.getMail(), RabbitConfig.ROUTING_KEY_3);
    }


    /**
     * Updates the user password based on the provided HTTP request.
     *
     * @param request the HTTP request containing the authorization header and the new password
     */
    @Override
    public void updatePassword(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String password = request.getParameter("password");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length()).trim();
            String login = jwtProvider.getLoginFromJwt(token);
            keyCloakService.updateUserPassword(login, password);
        } else {
            log.warn("Authorization header is missing or not in Bearer format. Header: {}", authorizationHeader);
        }
    }
}
