package fishinghelper.security_server.util;


import fishinghelper.security_server.dao.TokenRepository;
import fishinghelper.security_server.entity.Token;
import fishinghelper.security_server.exception.TokenExpiredException;
import fishinghelper.security_server.exception.TokenInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtFilterTest {
    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private CustomUserDetailService customUserDetailService;

    @Mock
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilter_ValidToken() throws IOException, ServletException {
//        String token = "validToken";
//        String login = "userLogin";
//        CustomUserDetail userDetails = mock(CustomUserDetail.class);
//
//        when(jwtProvider.validateToken(token)).thenReturn(true);
//        when(jwtProvider.getLogin(token)).thenReturn(login);
//        when(tokenRepository.findById(login)).thenReturn(Optional.of(new Token()));
//        when(customUserDetailService.loadUserByUsername(login)).thenReturn(userDetails);
//        when(userDetails.getAuthorities()).thenReturn(null);
//
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
//        ServletResponse response = mock(ServletResponse.class);
//        FilterChain filterChain = mock(FilterChain.class);
//
//        jwtFilter.doFilter(request, response, filterChain);
//
//        verify(filterChain).doFilter(request, response);
//        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
//        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    void testDoFilter_ExpiredToken() throws IOException, ServletException {
//        String token = "expiredToken";
//        when(jwtProvider.validateToken(token)).thenThrow(ExpiredJwtException.class);
//
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
//        ServletResponse response = mock(ServletResponse.class);
//        FilterChain filterChain = mock(FilterChain.class);
//
//        TokenExpiredException exception = assertThrows(TokenExpiredException.class, () ->
//                jwtFilter.doFilter(request, response, filterChain));
//
//        assertEquals(HttpStatus.FORBIDDEN, exception.getHttpStatus());
//        verify(filterChain, never()).doFilter(request, response);
    }

//    @Test
//    void testDoFilter_InvalidToken() throws IOException, ServletException {
//        String token = "invalidToken";
//        when(jwtProvider.validateToken(token)).thenThrow(JwtException.class);
//
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);
//        ServletResponse response = mock(ServletResponse.class);
//        FilterChain filterChain = mock(FilterChain.class);
//
//        TokenInvalidException exception = assertThrows(TokenInvalidException.class, () ->
//                jwtFilter.doFilter(request, response, filterChain));
//
//        assertEquals(HttpStatus.UNAUTHORIZED, exception.getHttpStatus());
//        verify(filterChain, never()).doFilter(request, response);
//    }

    @Test
    void testGetTokenRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer tokenValue");

        String token = jwtFilter.getTokenRequest(request);

        assertEquals("tokenValue", token);
    }

    @Test
    void testGetTokenRequest_NoToken() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        String token = jwtFilter.getTokenRequest(request);

        assertNull(token);
    }
}
