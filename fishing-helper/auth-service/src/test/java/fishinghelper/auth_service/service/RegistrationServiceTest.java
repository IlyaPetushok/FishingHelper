package fishinghelper.auth_service.service;

import fishinghelper.auth_service.dto.UserDTORequestRegistration;
import fishinghelper.auth_service.mapper.UserMapper;
import fishinghelper.common_module.dao.RoleRepositories;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.Role;
import fishinghelper.common_module.entity.user.RoleType;
import fishinghelper.common_module.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {
    @InjectMocks
    private RegistrationService registrationService; // The class containing the methods to test

    @Mock
    private UserRepositories userRepositories;

    @Mock
    private RoleRepositories roleRepositories;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserDTORequestRegistration request = new UserDTORequestRegistration();
        request.setPassword("plainpassword");
        request.setName("John Doe");
        request.setMail("john.doe@example.com");

        User user = new User();
        user.setName("John Doe");
        user.setMail("john.doe@example.com");
        user.setRoles(List.of());

        Role userRole = new Role();
        userRole.setName(RoleType.USER.name());

        when(passwordEncoder.encode("plainpassword")).thenReturn("encodedpassword");
        when(userMapper.toEntity(request)).thenReturn(user);
        when(roleRepositories.findRoleByName(RoleType.USER.name())).thenReturn(userRole);
        when(userRepositories.save(user)).thenReturn(user);
        doNothing().when(emailService).sendMessage(any(User.class), anyString(), anyString());

        registrationService.createUser(request);

        verify(passwordEncoder, times(1)).encode("plainpassword");
        verify(userMapper, times(1)).toEntity(request);
        verify(roleRepositories, times(1)).findRoleByName(RoleType.USER.name());
        verify(userRepositories, times(1)).save(user);
        verify(emailService, times(1)).sendMessage(eq(user), anyString(), anyString());
    }

    @Test
    void testUpdateStatusConfirmEmail() {
        String mail = "john.doe%40example.com";
        User user = new User();
        user.setMail("john.doe@example.com");
        user.setMailStatus(false);

        when(userRepositories.findUserByMail("john.doe@example.com")).thenReturn(user);
        when(userRepositories.save(user)).thenReturn(user);

        registrationService.updateStatusConfirmEmail(mail);

        assertTrue(user.isMailStatus());
        verify(userRepositories, times(1)).findUserByMail("john.doe@example.com");
        verify(userRepositories, times(1)).save(user);
    }
}
