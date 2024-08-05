package fishinghelper.security_server.util;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class JwtProvider implements Converter<Jwt, AbstractAuthenticationToken> {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.time}")
    private String timeExpire;

    @Value("${jwt.refresh.time}")
    private String timeRefreshExpire;

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =  new JwtGrantedAuthoritiesConverter();

    @Value("${spring.security.oauth2.client.provider.keycloak.user-name-attribute}")
    private String principleAttribute;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

//    @Autowired
//    public JwtProvider(JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter) {
//        this.jwtGrantedAuthoritiesConverter = jwtGrantedAuthoritiesConverter;
//    }

    @Override
    public AbstractAuthenticationToken convert(@NotNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        );
    }

    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (Objects.nonNull(principleAttribute)) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;

        if (Objects.isNull(jwt.getClaim("resource_access"))) {
            return Set.of();
        }
        resourceAccess = jwt.getClaim("resource_access");
        if (Objects.isNull(resourceAccess.get(clientId))) {
            return Set.of();
        }
        resource = (Map<String, Object>) resourceAccess.get(clientId);

        resourceRoles = (Collection<String>) resource.get("roles");
        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    //    /**
//     * Generates a refresh token for the specified user.
//     *
//     * @param user The User object for which the refresh token is generated.
//     * @return The generated refresh token.
//     */
//    public String generateRefreshToken(User user){
//        log.info("generate refresh token ...");
//        return buildToken(user,timeRefreshExpire);
//    }
//
//    /**
//     * Generates an access token for the specified user.
//     *
//     * @param user The User object for which the access token is generated.
//     * @return The generated access token.
//     */
//    public String generateToken(User user){
//        log.info("generate acess token ...");
//        return buildToken(user,timeExpire);
//    }
//
//    /**
//     * Builds a JWT token for the specified user with the given expiration time.
//     *
//     * @param user The User object for which the token is built.
//     * @param time The expiration time (in minutes) for the token.
//     * @return The generated JWT token as a String.
//     */
//    public String buildToken(User user,String time){
//        Date date = DateUtils.addMinutes(new Date(), Integer.parseInt(time));
//        Claims claims =  null;
////                Jwts.claims().setSubject(user.getLogin());
//        claims.put("role", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
//        claims.put("name", user.getName());
//        return Jwts.builder()
//                .setClaims(claims)
//                .setExpiration(date)
//                .signWith(getSignedKey())
//                .compact();
//    }
//
//    /**
//     * Validates the provided JWT token.
//     *
//     * @param token The JWT token to be validated.
//     * @return true if the token is valid (not expired), false otherwise.
//     */
//    public boolean validateToken(String token) {
////        Jws<Claims> claimsJws=Jwts.parserBuilder().setSigningKey(getSignedKey()).build().parseClaimsJws(token);
////        return !claimsJws.getBody().getExpiration().before(new Date());
//        return false;
//    }
//
//    /**
//     * Retrieves the login (subject) from the provided JWT token.
//     *
//     * @param token The JWT token from which to retrieve the login.
//     * @return The login (subject) extracted from the JWT token.
//     */
//    public String getLogin(String token) {
//        Claims claims = null;
////                Jwts.parserBuilder()
////                .setSigningKey(getSignedKey())
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
//        return claims.getSubject();
//    }
//
//    /**
//     * Retrieves the signing key used for JWT token validation.
//     *
//     * @return The signing key as a Key object.
//     */
//    private Key getSignedKey(){
//        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
}
