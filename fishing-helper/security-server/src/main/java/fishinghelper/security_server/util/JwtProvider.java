package fishinghelper.security_server.util;

import fishinghelper.common_module.entity.user.Role;
import fishinghelper.common_module.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service
@Data
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.time}")
    private String timeExpire;

    @Value("${jwt.refresh.time}")
    private String timeRefreshExpire;

    /**
     * Generates a refresh token for the specified user.
     *
     * @param user The User object for which the refresh token is generated.
     * @return The generated refresh token.
     */
    public String generateRefreshToken(User user){
        log.info("generate refresh token ...");
        return buildToken(user,timeRefreshExpire);
    }

    /**
     * Generates an access token for the specified user.
     *
     * @param user The User object for which the access token is generated.
     * @return The generated access token.
     */
    public String generateToken(User user){
        log.info("generate acess token ...");
        return buildToken(user,timeExpire);
    }

    /**
     * Builds a JWT token for the specified user with the given expiration time.
     *
     * @param user The User object for which the token is built.
     * @param time The expiration time (in minutes) for the token.
     * @return The generated JWT token as a String.
     */
    public String buildToken(User user,String time){
        Date date = DateUtils.addMinutes(new Date(), Integer.parseInt(time));
        Claims claims = Jwts.claims().setSubject(user.getLogin());
        claims.put("role", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        claims.put("name", user.getName());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(date)
                .signWith(getSignedKey())
                .compact();
    }

    /**
     * Validates the provided JWT token.
     *
     * @param token The JWT token to be validated.
     * @return true if the token is valid (not expired), false otherwise.
     */
    public boolean validateToken(String token) {
        Jws<Claims> claimsJws=Jwts.parserBuilder().setSigningKey(getSignedKey()).build().parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    /**
     * Retrieves the login (subject) from the provided JWT token.
     *
     * @param token The JWT token from which to retrieve the login.
     * @return The login (subject) extracted from the JWT token.
     */
    public String getLogin(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /**
     * Retrieves the signing key used for JWT token validation.
     *
     * @return The signing key as a Key object.
     */
    private Key getSignedKey(){
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
