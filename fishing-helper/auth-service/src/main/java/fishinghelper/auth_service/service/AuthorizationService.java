package fishinghelper.auth_service.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.auth_service.dto.AuthenticationDTOResponse;
import fishinghelper.auth_service.dto.UserDTORequestAuthorization;
import fishinghelper.auth_service.exception.InvalidDataException;
import fishinghelper.auth_service.exception.UserNotFoundException;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.security_server.dao.TokenRepository;
import fishinghelper.security_server.entity.Token;
import fishinghelper.security_server.exception.TokenExpiredException;
import fishinghelper.security_server.util.JwtFilter;
import fishinghelper.security_server.util.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;

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
    private static final String BEARER = "Bearer ";

    private final TokenRepository tokenRepository;
    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;
    private final JwtFilter jwtFilter;
    private final JwtProvider jwtProvider;
    private final EmailService emailService;

    @Autowired
    public AuthorizationService(TokenRepository tokenRepository, UserRepositories userRepositories, PasswordEncoder passwordEncoder, JwtFilter jwtFilter, JwtProvider jwtProvider, EmailService emailService) {
        this.tokenRepository = tokenRepository;
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.jwtFilter = jwtFilter;
        this.jwtProvider = jwtProvider;
        this.emailService = emailService;
    }

    /**
     * Handles user authorization based on the provided credentials.
     * Generates JWT token and refresh token upon successful authorization.
     *
     * @param userDTORequestAuthorization DTO containing user login and password
     * @return AuthenticationDTOResponse containing JWT token and refresh token
     * @throws InvalidDataException if user login or password is invalid
     */

    public AuthenticationDTOResponse userAuthorization(UserDTORequestAuthorization userDTORequestAuthorization) {
        String login = userDTORequestAuthorization.getLogin();
        log.info("Authorizing user: {}", login);

        User user = userRepositories.findUserByLogin(login);
        if (Objects.isNull(user)) {
            log.warn("User {} not found", login);
            throw new InvalidDataException(HttpStatus.UNAUTHORIZED, "invalid data for authorize");
        }

        if (!passwordEncoder.matches(userDTORequestAuthorization.getPassword(), user.getPassword())) {
            log.warn("Invalid password for user {}", userDTORequestAuthorization.getPassword());
            throw new InvalidDataException(HttpStatus.UNAUTHORIZED, "invalid data for authorize");
        }

        String token = jwtProvider.generateToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        tokenRepository.save(new Token(login, token));

        log.info("User {} authorized successfully", login + " " + userDTORequestAuthorization.getPassword());
        return new AuthenticationDTOResponse(token, refreshToken);
    }


    /**
     * Refreshes the JWT token using the provided refresh token.
     * Updates the token in the database and returns the updated tokens in the response.
     *
     * @param request  HTTP request containing the refresh token in the Authorization header
     * @param response HTTP response where the updated tokens are written
     * @throws TokenExpiredException if the refresh token is expired
     */

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!hasText(bearer) && !bearer.startsWith(BEARER)) {
            log.warn("Authorization header is missing or invalid");
            return;
        }

        String refreshToken = bearer.replace(BEARER, "").trim();
        String login = jwtProvider.getLogin(refreshToken);

        if (login != null) {
            User user = userRepositories.findUserByLogin(login);

            if (jwtProvider.validateToken(refreshToken)) {
                String token = jwtProvider.generateToken(user);
                tokenRepository.save(new Token(login, token));

                AuthenticationDTOResponse authenticationDTOResponse = new AuthenticationDTOResponse(token, refreshToken);
                try (OutputStream outputStream = response.getOutputStream()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(outputStream, authenticationDTOResponse);

                    log.info("Token refreshed successfully for user {}", login);
                } catch (IOException e) {
                    log.error("Failed to write response data", e);
                    e.printStackTrace();
                }
            } else {
                log.warn("Token expired for user {}", login);
                throw new TokenExpiredException(HttpStatus.FORBIDDEN, "token has expired");
            }
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

    /**
     * Updates the user's password based on data from an HTTP request.
     *
     * @param request the HTTP request containing parameters, including the new password
     */
    public void updatePassword(HttpServletRequest request) {
        String password = request.getParameter("password");
        String jwt = jwtFilter.getTokenRequest(request);
        String login = jwtProvider.getLogin(jwt);

        User user = userRepositories.findUserByLogin(login);

        if (user == null) {
            log.error("User not found for login: " + login);
            throw new UserNotFoundException(HttpStatus.NOT_FOUND, "user not found by login:" + login);
        }

        user.setPassword(passwordEncoder.encode(password));
        userRepositories.save(user);
    }
}
