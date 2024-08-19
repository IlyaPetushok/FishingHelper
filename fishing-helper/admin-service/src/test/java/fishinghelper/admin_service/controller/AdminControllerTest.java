package fishinghelper.admin_service.controller;

import fishinghelper.admin_service.dto.ConstrainDTO;
import fishinghelper.admin_service.dto.UserDTORequest;
import fishinghelper.admin_service.dto.UserDTOResponse;
import fishinghelper.admin_service.dto.filter.UserDTOFilter;
import fishinghelper.admin_service.service.AdminService;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration( classes = {AdminController.class})
@WebMvcTest(AdminController.class)
@ImportAutoConfiguration(exclude = {SecurityAutoConfiguration.class, OAuth2AuthorizationServerJwtAutoConfiguration.class, OAuth2ResourceServerAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
public class AdminControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AdminService adminService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAdminUpdateRole_Success() throws Exception {
        String jsonRequest = "{\"field\":\"value\"}";

        mockMvc.perform(put("/admin/user/1/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(adminService).updateRoleForUser(any(UserDTORequest.class), anyInt());
    }

    @Test
    public void testFindUserByFilter_Success() throws Exception {
        String jsonRequest = "{\"filter\":\"value\"}";

        when(adminService.findUserByFilter(any(UserDTOFilter.class)))
                .thenReturn(List.of(new UserDTOResponse()));

        mockMvc.perform(post("/admin/users/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void testUserSetConstrain_Success() throws Exception {
        String jsonRequest = "{\"constraint\":\"value\"}";

        mockMvc.perform(post("/admin/users/1/constrain")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(adminService).setConstrainUser(any(ConstrainDTO.class), anyInt());
    }

}
