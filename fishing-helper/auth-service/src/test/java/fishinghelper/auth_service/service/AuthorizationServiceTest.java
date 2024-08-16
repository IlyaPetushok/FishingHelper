package fishinghelper.auth_service.service;

import fishinghelper.auth_service.dto.TokenRequest;
import fishinghelper.auth_service.dto.UserDTORequestAuthorization;
import fishinghelper.auth_service.exception.RefreshTokenException;
import fishinghelper.auth_service.service.impl.AuthorizationServiceImpl;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.notification_service.messaging.producer.RabbitMQProducer;
import fishinghelper.security_server.service.KeyCloakService;
import fishinghelper.security_server.util.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorizationServiceTest {
    @Mock
    private UserRepositories userRepositories;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private KeyCloakService keyCloakService;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthorizationServiceImpl authService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testUserAuthorization() {
//        when(userRepositories.findUserByLogin(any(String.class))).thenReturn(new User());
//
//        ResponseEntity<Object> mockResponse = new ResponseEntity<>(HttpStatus.OK);
//
//        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(Object.class)))
//                .thenCallRealMethod();
//
//        UserDTORequestAuthorization dto = new UserDTORequestAuthorization();
//        dto.setLogin("testuser");
//        dto.setPassword("testpass");
//
//        ResponseEntity<?> response = authService.userAuthorization(dto);
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        verify(restTemplate, times(1)).postForEntity(any(String.class), any(HttpEntity.class), eq(Object.class));
//    }
//
//    @Test
//    void testRefreshToken_Success() {
//        ResponseEntity<Object> mockResponse = new ResponseEntity<>(HttpStatus.OK);
//        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(Object.class)))
//                .thenReturn(mockResponse);
//
//        TokenRequest tokenRequest = new TokenRequest("mock-refresh-token");
//
//        ResponseEntity<?> response = authService.refreshToken(tokenRequest);
//
//        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(Object.class));
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void testRefreshToken_Failure() {
//        ResponseEntity<Object> mockResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(Object.class)))
//                .thenReturn(mockResponse);
//
//        TokenRequest tokenRequest = new TokenRequest("mock-refresh-token");
//
//        RefreshTokenException exception = assertThrows(
//                RefreshTokenException.class,
//                () -> authService.refreshToken(tokenRequest)
//        );
//
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
//        assertEquals("Failed to refresh token", exception.getMessage());
//    }


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
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer validToken");
        when(request.getParameter("password")).thenReturn("newPassword");
        when(jwtProvider.getLoginFromJwt("validToken")).thenReturn("userLogin");

        authService.updatePassword(request);

        verify(jwtProvider, times(1)).getLoginFromJwt("validToken");
        verify(keyCloakService, times(1)).updateUserPassword("userLogin", "newPassword");
    }
}
