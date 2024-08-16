package fishinghelper.auth_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.auth_service.dto.TokenRequest;
import fishinghelper.auth_service.dto.UserDTORequestAuthorization;
import fishinghelper.auth_service.service.impl.AuthorizationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerJwtAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration( classes = {AuthenticationController.class})
@WebMvcTest(AuthenticationController.class)
@ImportAutoConfiguration(exclude = {SecurityAutoConfiguration.class, OAuth2AuthorizationServerJwtAutoConfiguration.class, OAuth2ResourceServerAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
@PropertySource(value = "classpath:application.properties")
public class AuthenticationControllerTest  {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationServiceImpl authorizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthorization() throws Exception {
        UserDTORequestAuthorization requestDto = new UserDTORequestAuthorization();
        requestDto.setLogin("login");
        requestDto.setPassword("password");

        when(authorizationService.userAuthorization(any(UserDTORequestAuthorization.class)))
                .thenReturn(any());

        mockMvc.perform(post("/auth/authorization")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(authorizationService, times(1)).userAuthorization(any(UserDTORequestAuthorization.class));
    }

    @Test
    void testRefreshToken() throws Exception {
        TokenRequest tokenRequest=new TokenRequest();
        mockMvc.perform(post("/auth/refresh-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tokenRequest)))
                .andExpect(status().isOk());
        verify(authorizationService, times(1)).refreshToken(any());
    }

    @Test
    void testRequestUpdatePassword() throws Exception {
        Integer userId = 1;

        mockMvc.perform(get("/auth/update/user/{id}/password", userId)).andExpect(status().isOk());

        verify(authorizationService, times(1)).requestUpdatePassword(userId);
    }

    @Test
    void testUpdatePassword() throws Exception {
        mockMvc.perform(post("/auth/update/password")
                        .param("password", "newPassword"))
                .andExpect(status().isOk());

        verify(authorizationService, times(1)).updatePassword(any(HttpServletRequest.class));
    }
}