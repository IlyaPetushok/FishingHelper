package fishinghelper.security_server.util;


import fishinghelper.common_module.entity.user.Role;
import fishinghelper.common_module.entity.user.RoleType;
import fishinghelper.common_module.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {JwtProvider.class})
@PropertySource(value = "classpath:application.properties")
public class JwtProviderTest {

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${jwt.secret}")
    private String secretKey;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
//        jwtProvider.setSecretKey(secretKey);
//        jwtProvider.setTimeExpire("60");
//        jwtProvider.setTimeRefreshExpire("1440");
    }

    @Test
    void testGenerateRefreshToken() {
//        User user = new User();
//        user.setLogin("userLogin");
//        user.setRoles(List.of(new Role(RoleType.USER.name())));
//        user.setName("User Name");
//
//        String token = jwtProvider.generateRefreshToken(user);
//
//        assertNotNull(token);
//        assertTrue(jwtProvider.validateToken(token));
    }

    @Test
    void testGenerateToken() {
//        User user = new User();
//        user.setLogin("userLogin");
//        user.setRoles(List.of(new Role("ROLE_USER")));
//        user.setName("User Name");
//
//        String token = jwtProvider.generateToken(user);
//
//        assertNotNull(token);
//        assertTrue(jwtProvider.validateToken(token));
    }

    @Test
    void testBuildToken() {
//        User user = new User();
//        user.setLogin("userLogin");
//        user.setRoles(List.of(new Role("ROLE_USER")));
//        user.setName("User Name");
//
//        String token = jwtProvider.buildToken(user, "30");
//
//        assertNotNull(token);
//        assertTrue(jwtProvider.validateToken(token));
//
//        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
//
//        Claims claims = null;
////                Jwts.parserBuilder()
////                .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
//
//        assertEquals("userLogin", claims.getSubject());
//        assertEquals("User Name", claims.get("name"));
//        assertTrue(((List<?>) claims.get("role")).contains("ROLE_USER"));
    }

    @Test
    void testValidateToken() {
//        User user = new User();
//        user.setLogin("userLogin");
//        user.setRoles(List.of(new Role("ROLE_USER")));
//        user.setName("User Name");
//
//        String token = jwtProvider.generateToken(user);
//        assertTrue(jwtProvider.validateToken(token));
    }

    @Test
    void testGetLogin() {
//        User user = new User();
//        user.setLogin("userLogin");
//        user.setRoles(List.of(new Role("ROLE_USER")));
//        user.setName("User Name");
//
//        String token = jwtProvider.generateToken(user);
//        String login = jwtProvider.getLogin(token);
//
//        assertEquals("userLogin", login);
    }
}
