package fishinghelper.security_server.service;

import fishinghelper.security_server.dao.TokenRepository;
import fishinghelper.security_server.util.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.hasText;


@Service
@Slf4j
public class LogoutService implements LogoutHandler {
    public static final String BEARER = "Bearer ";
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public LogoutService(TokenRepository tokenRepository, JwtProvider jwtProvider) {
        this.tokenRepository = tokenRepository;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Performs logout operation for a user based on the JWT token extracted from the request.
     *
     * <p>First, it checks if the Authorization header contains a valid JWT token starting with "Bearer".
     * If not, the method returns without performing any action.
     *
     * <p>The method extracts the JWT token, retrieves the login information using {@code jwtProvider},
     * and logs the logout operation including the user's login.
     * It then deletes the token from the {@code tokenRepository} associated with the user's login.
     *
     * @param request        The HttpServletRequest containing the HTTP request information.
     * @param response       The HttpServletResponse for sending HTTP responses.
     * @param authentication The Authentication object representing the current user's authentication details.
     */

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!hasText(bearer) || !bearer.startsWith(BEARER)) {
            return;
        }
        String jwt=bearer.replace(BEARER, "").trim();;
        String login=jwtProvider.getLogin(jwt);

        log.info("Выполняется выход пользователя с логином: {}", login);

        tokenRepository.deleteById(login);

        log.info("Токен пользователя с логином {} успешно удален", login);
    }
}
