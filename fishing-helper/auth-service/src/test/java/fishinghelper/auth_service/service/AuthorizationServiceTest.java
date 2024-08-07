package fishinghelper.auth_service.service;

import fishinghelper.auth_service.dto.UserDTORequestAuthorization;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.notification_service.messaging.producer.RabbitMQProducer;
import fishinghelper.security_server.dao.TokenRepository;
import fishinghelper.security_server.util.JwtFilter;
import fishinghelper.security_server.util.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorizationServiceTest {
    @InjectMocks
    private AuthorizationService authService;

    @Mock
    private UserRepositories userRepositories;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserAuthorization() {
        UserDTORequestAuthorization request = new UserDTORequestAuthorization("test_user", "test_password");

        ResponseEntity<Object> mockResponseEntity = new ResponseEntity<>(HttpStatus.OK);

        when(restTemplate.postForEntity(any(), any(), any()))
                .thenReturn(mockResponseEntity);

        ResponseEntity<?> responseEntity = authService.userAuthorization(request);
    }


    @Test
    void testRefreshToken() throws IOException {

        ResponseEntity<?> mockResponseEntity = new ResponseEntity<>(HttpStatus.OK);

        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(mockResponseEntity);
    }

    @Test
    void testRequestUpdatePassword() {
        Integer userId = 1;
        User user = new User();
        user.setMail("test@example.com");

        when(userRepositories.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(rabbitMQProducer).sendMessageQueue(eq(user.getMail()), anyString());

        authService.requestUpdatePassword(userId);

        verify(rabbitMQProducer, times(1)).sendMessageQueue(eq(user.getMail()), anyString());
    }

    @Test
    void testUpdatePassword() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        String password = "newpassword";
        String jwt = "jwtToken";
        String login = "testuser";
        String encodedPassword = "encodedpassword";

        User user = new User();
        user.setPassword(encodedPassword);

        when(request.getParameter("password")).thenReturn(password);
        when(jwtFilter.getTokenRequest(request)).thenReturn(jwt);
        when(jwtProvider.getLoginFromJwt(jwt)).thenReturn(login);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        authService.updatePassword(request);

    }
}
