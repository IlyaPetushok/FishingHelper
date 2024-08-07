package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.RoleDTO;
import fishinghelper.common_module.entity.user.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {RoleMapperImpl.class})
public class RoleMapperTest {
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testToEntities() {
        List<RoleDTO> roleDTOList = new ArrayList<>();

        RoleDTO roleDTO1 = new RoleDTO();
        roleDTO1.setId(1);
        roleDTOList.add(roleDTO1);

        RoleDTO roleDTO2 = new RoleDTO();
        roleDTO2.setId(2);
        roleDTOList.add(roleDTO2);

        List<Role> roles = roleMapper.toEntities(roleDTOList);

        assertNotNull(roles);
        assertEquals(2, roles.size());

        Role role1 = roles.get(0);
        assertEquals(roleDTO1.getId(), role1.getId());

        Role role2 = roles.get(1);
        assertEquals(roleDTO2.getId(), role2.getId());
    }
}
