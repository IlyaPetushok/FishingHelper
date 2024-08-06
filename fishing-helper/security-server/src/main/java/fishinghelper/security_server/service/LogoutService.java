package fishinghelper.security_server.service;

import fishinghelper.security_server.dao.TokenRepository;
import fishinghelper.security_server.util.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.springframework.util.StringUtils.hasText;


@Service
@Slf4j
public class LogoutService implements LogoutHandler {
    public static final String BEARER = "Bearer ";
    private final RestTemplate restTemplate=new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.keycloak.logout.url}")
    private String logoutUrl;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!hasText(bearer) || !bearer.startsWith(BEARER)) {
//            except
            System.out.println("exception");
        }
        String token=bearer.replace(BEARER, "").trim();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String,String> multiValueMap= new LinkedMultiValueMap<>();
        multiValueMap.add("client_id",clientId);
        multiValueMap.add("client_secret", clientSecret);
        multiValueMap.add("refresh_token", token);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap,headers);

        restTemplate.postForEntity(logoutUrl, httpEntity, Object.class);

    }
}
