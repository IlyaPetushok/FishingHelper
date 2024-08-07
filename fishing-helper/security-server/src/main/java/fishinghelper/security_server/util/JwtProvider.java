package fishinghelper.security_server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
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
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public AbstractAuthenticationToken convert(@NotNull Jwt jwt) {
        checkActiveSession(jwt);
        String login = jwt.getClaimAsString(principleAttribute);

        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                customUserDetailService.loadUserByUsername(login).getAuthorities().stream()
        ).collect(Collectors.toSet());

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        );
    }

    public void checkActiveSession(Jwt jwt) {
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

    private void handleResponse(ResponseEntity<String> response) {
        if (response.getStatusCode() == HttpStatus.OK) {
            processResponseBody(response.getBody());
        } else {
            throw new RuntimeException("Failed to introspect token: " + response.getStatusCode());
        }
    }

    private void processResponseBody(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
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
            throw new RuntimeException("Failed to process token introspection response.", e);
        }
    }

    public String getLoginFromJwt(String token) {
        JwtDecoder jwtDecoder = JwtDecoders.fromIssuerLocation(urlRealm);
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaimAsString(principleAttribute);
    }

    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (Objects.nonNull(principleAttribute)) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }

}
