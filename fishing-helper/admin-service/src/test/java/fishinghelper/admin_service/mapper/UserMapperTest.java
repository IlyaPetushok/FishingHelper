package fishinghelper.admin_service.mapper;

import fishinghelper.admin_service.dto.UserDTOResponse;
import fishinghelper.common_module.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {UserMapperImpl.class})
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testToDTOResponse() {
        User user = new User();
        user.setId(1);
        user.setName("john_doe");
        user.setMail("john.doe@example.com");

        UserDTOResponse dtoResponse = userMapper.toDTOResponse(user);

        assertNotNull(dtoResponse);
        assertEquals(user.getName(), dtoResponse.getName());
        assertEquals(user.getMail(), dtoResponse.getMail());
    }
}
