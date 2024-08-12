package fishinghelper.security_server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.*;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Provides methods for handling JWT tokens including introspection and converting JWT tokens to authentication tokens.
 *
 * <p>Author: Ilya Petushok</p>
 */
@Slf4j
@Service
public class JwtProvider implements Converter<Jwt, AbstractAuthenticationToken> {
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.url.token}")
    private String urlOpenIdToken;


    @Value("${spring.security.oauth2.client.provider.keycloak.user-name-attribute}")
    private String principleAttribute;

    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String urlRealm;

    private final RestTemplate restTemplate = new RestTemplate();
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public JwtProvider(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    /**
     * Converts a JWT token to an {@link AbstractAuthenticationToken} for Spring Security.
     *
     * @param jwt The JWT token to convert.
     * @return An {@link AbstractAuthenticationToken} containing the JWT and authorities.
     */
    @SneakyThrows
    @Override
    public AbstractAuthenticationToken convert(@NotNull Jwt jwt) {
        checkActiveSession(jwt);

        String login = jwt.getClaimAsString(principleAttribute);
        List<String> roleNames = getStrings(jwt.getClaims());

        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                customUserDetailService.loadUserByUsername(login, roleNames).stream()
        ).collect(Collectors.toSet());

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        );
    }

    @SuppressWarnings("unchecked")
    private List<String> getStrings(Map<String, Object> claims) {
        Map<String, Object> resource_access = (Map<String, Object>) claims.get("resource_access");
        Map<String, Object> accounts = (Map<String, Object>) resource_access.get("fishing-helper-rest-api");
        return (List<String>) accounts.get("roles");
    }

    /**
     * Checks if the given JWT token is active by performing introspection.
     *
     * @param jwt The JWT token to check.
     */
    public void checkActiveSession(Jwt jwt) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("token", jwt.getTokenValue());

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(urlOpenIdToken + "/introspect", requestEntity, String.class);
        handleResponse(response);
    }

    /**
     * Handles the response from the token introspection endpoint.
     *
     * @param response The response from the introspection endpoint.
     */
    private void handleResponse(ResponseEntity<String> response) throws JsonProcessingException {
        if (response.getStatusCode() == HttpStatus.OK) {
            processResponseBody(response.getBody());
        } else {
            log.error("Failed to introspect token. HTTP status: " + response.getStatusCode());
        }
    }

    /**
     * Processes the response body to check if the token is active.
     *
     * @param responseBody The response body from the introspection endpoint.
     */
    private void processResponseBody(String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        boolean isActive = jsonNode.get("active").asBoolean();
        if (isActive) {
            log.info("Token is active.");
        } else {
            log.warn("Token is inactive or invalid.");
        }
    }

    /**
     * Retrieves the login (principal) from the JWT token.
     *
     * @param token The JWT token from which to retrieve the login.
     * @return The login (principal) extracted from the JWT token.
     */
    public String getLoginFromJwt(String token) {
        JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(urlRealm);
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaimAsString(principleAttribute);
    }


    /**
     * Retrieves the claim name for the principal from the JWT.
     *
     * @param jwt The JWT token from which to retrieve the claim name.
     * @return The claim name for the principal.
     */
    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (Objects.nonNull(principleAttribute)) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }

}
