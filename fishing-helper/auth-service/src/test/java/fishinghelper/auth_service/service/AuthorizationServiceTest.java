package fishinghelper.auth_service.service;

import fishinghelper.auth_service.dto.AuthenticationDTOResponse;
import fishinghelper.auth_service.dto.UserDTORequestAuthorization;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.security_server.dao.TokenRepository;
import fishinghelper.security_server.entity.Token;
import fishinghelper.security_server.util.JwtFilter;
import fishinghelper.security_server.util.JwtProvider;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private TokenRepository tokenRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserAuthorization() {
        String login = "testuser";
        String password = "password";
        String encodedPassword = "encodedpassword";
        String token = "token";
        String refreshToken = "refreshToken";

        UserDTORequestAuthorization request = new UserDTORequestAuthorization();
        request.setLogin(login);
        request.setPassword(password);

        User user = new User();
        user.setPassword(encodedPassword);

        when(userRepositories.findUserByLogin(login)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);
        when(jwtProvider.generateToken(user)).thenReturn(token);
        when(jwtProvider.generateRefreshToken(user)).thenReturn(refreshToken);
        when(tokenRepository.save(any(Token.class))).thenReturn(new Token(login, token));

        AuthenticationDTOResponse response = authService.userAuthorization(request);

        assertNotNull(response);
        assertEquals(token, response.getJwtToken());
        assertEquals(refreshToken, response.getRefreshToken());
    }


    @Test
    void testRefreshToken() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        String bearerToken = "Bearer refreshToken";
        String login = "testuser";
        String newToken = "newToken";
        String refreshToken = "refreshToken";

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(bearerToken);
        when(jwtProvider.getLogin(refreshToken)).thenReturn(login);
        when(jwtProvider.validateToken(refreshToken)).thenReturn(true);
        when(jwtProvider.generateToken(any(User.class))).thenReturn(newToken);
        when(userRepositories.findUserByLogin(login)).thenReturn(new User());
        when(tokenRepository.save(any(Token.class))).thenReturn(new Token(login, newToken));

        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStream);

        authService.refreshToken(request, response);

        verify(tokenRepository, times(1)).save(any(Token.class));
        verify(response, times(1)).getOutputStream();
    }

    @Test
    void testRequestUpdatePassword() {
        Integer userId = 1;
        User user = new User();
        user.setMail("test@example.com");

        when(userRepositories.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(emailService).sendMessage(eq(user), anyString(), anyString());

        authService.requestUpdatePassword(userId);

        verify(emailService, times(1)).sendMessage(eq(user), anyString(), anyString());
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
        when(jwtProvider.getLogin(jwt)).thenReturn(login);
        when(userRepositories.findUserByLogin(login)).thenReturn(user);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        authService.updatePassword(request);

        verify(userRepositories, times(1)).save(user);
    }
}
