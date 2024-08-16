package fishinghelper.auth_service.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import fishinghelper.auth_service.dto.UserDTORequestRegistration;
import fishinghelper.auth_service.service.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration( classes = {RegistrationController.class})
@WebMvcTest(RegistrationController.class)
@ImportAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrationUser() throws Exception {
        UserDTORequestRegistration userDTO = new UserDTORequestRegistration();

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

        verify(registrationService, times(1)).createUser(any(UserDTORequestRegistration.class));
    }

    @Test
    public void testConfirmMail() throws Exception {
        String email = "test@example.com";

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/confirm/email/{mail}", email))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        verify(registrationService, times(1)).updateStatusConfirmEmail(email);
    }
}
