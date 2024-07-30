package fishinghelper.admin_service.service;

import fishinghelper.admin_service.dto.ConstrainDTO;
import fishinghelper.admin_service.dto.RoleDTO;
import fishinghelper.admin_service.dto.UserDTORequest;
import fishinghelper.admin_service.mapper.RoleMapper;
import fishinghelper.admin_service.service.impl.AdminServiceImpl;
import fishinghelper.common_module.dao.PrivilegesRepository;
import fishinghelper.common_module.dao.RoleRepositories;
import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.Privileges;
import fishinghelper.common_module.entity.user.Role;
import fishinghelper.common_module.entity.user.RoleType;
import fishinghelper.common_module.entity.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AdminServiceTest {
    @Mock
    private UserRepositories userRepositories;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private RoleRepositories roleRepositories;

    @Mock
    private PrivilegesRepository privilegesRepository;


    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateRoleForUser() {
        UserDTORequest userDTORequest = new UserDTORequest();
        userDTORequest.setRoles(List.of(new RoleDTO(1)));

        User user = new User();
        user.setId(1);

        when(userRepositories.findById(anyInt())).thenReturn(Optional.of(user));
        when(roleMapper.toEntities(anyList())).thenReturn(List.of(new Role()));
        when(userRepositories.save(any(User.class))).thenReturn(user);

        adminService.updateRoleForUser(userDTORequest, 1);

        verify(userRepositories, times(1)).findById(anyInt());
        verify(roleMapper, times(1)).toEntities(anyList());
        verify(userRepositories, times(1)).save(any(User.class));
    }

    @Test
    void testCheckUpdateUserRole() {
        UserDTORequest userDTORequest = new UserDTORequest();
        userDTORequest.setRoles(Collections.singletonList(new RoleDTO(1)));
        Role role=new Role();
        role.setName(RoleType.USER.name());

        User user = new User();
        user.setRoles(List.of(role));

        when(userRepositories.findById(anyInt())).thenReturn(Optional.of(user));
        when(roleRepositories.findAllById(anyList())).thenReturn(Collections.singletonList(role));

        boolean hasPermission = adminService.checkUpdateUserRole(userDTORequest, 1);

        assertTrue(hasPermission);
        verify(userRepositories, times(1)).findById(anyInt());
        verify(roleRepositories, times(1)).findAllById(anyList());
    }

    @Test
    void testSetConstrainUser() {
        ConstrainDTO constrainDTO = new ConstrainDTO();
        constrainDTO.setName(List.of("READ_PRIVILEGE"));

        Privileges newPrivilege = new Privileges();
        newPrivilege.setName("READ_PRIVILEGE");

        User user = new User();
        user.setPrivileges(new ArrayList<>());

        when(privilegesRepository.findAllByNameIn(anyList())).thenReturn(Collections.singletonList(newPrivilege));
        when(userRepositories.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRepositories.save(any(User.class))).thenReturn(user);

        adminService.setConstrainUser(constrainDTO, 1);

        assertEquals(1, user.getPrivileges().size());
        assertEquals("READ_PRIVILEGE", user.getPrivileges().get(0).getName());
        verify(privilegesRepository, times(1)).findAllByNameIn(anyList());
        verify(userRepositories, times(1)).findById(anyInt());
        verify(userRepositories, times(1)).save(any(User.class));
    }
}
