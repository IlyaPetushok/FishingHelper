package fishinghelper.security_server.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.springframework.util.StringUtils.hasText;

/**
 * Service class for handling user authentication operations such as logout.
 * <p>
 * Author: Ilya Petushok
 */
@Service
@Slf4j
public class LogoutService implements LogoutHandler {
    public static final String BEARER = "Bearer ";
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.keycloak.logout.url}")
    private String logoutUrl;

    /**
     * Logs out a user by invalidating their refresh token.
     *
     * @param request        The HTTP request containing the authorization header.
     * @param response       The HTTP response to be sent.
     * @param authentication The authentication object representing the current user.
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!hasText(bearer) || !bearer.startsWith(BEARER)) {
            log.error("Authorization header is missing or does not start with Bearer.");
        }
        String token = bearer.replace(BEARER, "").trim();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("client_id", clientId);
        multiValueMap.add("client_secret", clientSecret);
        multiValueMap.add("refresh_token", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, headers);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity(logoutUrl, httpEntity, Object.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK || responseEntity.getStatusCode() == HttpStatus.NO_CONTENT) {
            log.info("User successfully logged out.");
        } else {
            log.error("Failed to log out user. HTTP status: " + responseEntity.getStatusCode());
        }

    }
}
