package fishinghelper.security_server.util;

import fishinghelper.security_server.dao.TokenRepository;
import fishinghelper.security_server.exception.TokenExpiredException;
import fishinghelper.security_server.exception.TokenInvalidException;
import fishinghelper.security_server.exception.TokenNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
//@Service
public class JwtFilter extends GenericFilterBean {

    public static final String BEARER = "Bearer ";
//    private final JwtProvider jwtProvider;
//    private final CustomUserDetailService customUserDetailService;
//    private final TokenRepository tokenRepository;

//    @Autowired
    public JwtFilter(JwtProvider jwtProvider, CustomUserDetailService customUserDetailService, TokenRepository tokenRepository) {
//        this.jwtProvider = jwtProvider;
//        this.customUserDetailService = customUserDetailService;
//        this.tokenRepository = tokenRepository;
    }

    /**
     * Implements a servlet filter to validate and process JWT tokens for authentication.
     *
     * <p>This method intercepts incoming requests to validate the JWT token present in the Authorization header.
     * If the token is valid, it retrieves the user details from the token, checks its validity in the {@code tokenRepository},
     * and sets the authentication context using {@code SecurityContextHolder}.
     *
     * <p>If the token is expired or invalid, appropriate exceptions are thrown indicating the failure reason.
     *
     * @param servletRequest  The ServletRequest object representing the HTTP request.
     * @param servletResponse The ServletResponse object representing the HTTP response.
     * @param filterChain     The FilterChain for invoking the next filter or servlet in the chain.
     * @throws IOException      If an I/O error occurs during the filter execution.
     * @throws ServletException If an exception occurs while processing the filter chain.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        String token = getTokenRequest((HttpServletRequest) servletRequest);
//        try {
//            if (token != null && jwtProvider.validateToken(token)) {
//                log.info("token has not expire");
//                String login = jwtProvider.getLogin(token);
//                if (tokenRepository.findById(login).isEmpty()) {
//                    log.error("token become is not valid");
//                    throw new TokenNotFoundException(HttpStatus.FORBIDDEN, "This token become is not valid");
//                }
//                CustomUserDetail customUserDetails = customUserDetailService.loadUserByUsername(login);
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        } catch (ExpiredJwtException e) {
//            logger.error("Token has expired: {}"+ e.getMessage());
//            throw new TokenExpiredException(HttpStatus.FORBIDDEN, "Token has expired");
//        } catch (IllegalArgumentException e) {
//            logger.error("Unable to validate JWT token: {}"+ e.getMessage());
//            throw new TokenInvalidException(HttpStatus.UNAUTHORIZED, "Unable to validate JWT token");
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
    }


    /**
     * Retrieves the JWT token from the Authorization header of the HTTP request.
     *
     * @param httpServletRequest The HttpServletRequest object representing the HTTP request.
     * @return The JWT token extracted from the Authorization header, or null if no token is found.
     */
    public String getTokenRequest(HttpServletRequest httpServletRequest) {
        String bearer = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith(BEARER)) {
            return bearer.replace(BEARER, "").trim();
        }
        return null;
    }
}
