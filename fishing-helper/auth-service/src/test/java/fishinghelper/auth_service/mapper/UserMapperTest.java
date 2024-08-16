package fishinghelper.auth_service.mapper;

import fishinghelper.auth_service.dto.UserDTORequestRegistration;
import fishinghelper.common_module.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@SpringBootTest(classes = {UserMapperImpl.class})// worked
@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testToEntity() {
        UserDTORequestRegistration userDTORequest = new UserDTORequestRegistration();
        userDTORequest.setName("testuser");
        userDTORequest.setMail("testuser@example.com");

        User user = userMapper.toEntity(userDTORequest);

        assertNotNull(user);
        assertEquals("testuser", user.getName());
        assertEquals("testuser@example.com", user.getMail());
    }
}
