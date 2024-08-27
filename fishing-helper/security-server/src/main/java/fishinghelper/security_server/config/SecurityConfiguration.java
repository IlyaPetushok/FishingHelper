package fishinghelper.security_server.config;

import fishinghelper.security_server.util.JwtProvider;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true)
@ComponentScan(basePackages = {"fishinghelper.common_module"})
public class SecurityConfiguration {
    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.keycloak.admin.name}")
    private String adminName;

    @Value("${spring.security.keycloak.admin.password}")
    private String adminPassword;

    @Value("${spring.security.keycloak.realm.name}")
    private String realmName;

    @Value("${spring.security.keycloak.server.url}")
    private String serverUrl;

    private final LogoutHandler logoutHandler;
    private final JwtProvider jwtProvider;

    @Autowired
    public SecurityConfiguration(LogoutHandler logoutHandler, JwtProvider jwtProvider) {
        this.logoutHandler = logoutHandler;
        this.jwtProvider = jwtProvider;
    }

    private static final String[] SWAGGER_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources"
    };

    private static final String[] USER_SERVICE_ENDPOINT = {
            "/user/place*",
            "/user/place/*"
    };

    private static final String[] AUTH_SERVICE_ENDPOINT = {
            "/auth/registration",
            "/auth/authorization",
            "/logout",
            "/auth/update/**",
            "/auth/introspect",
            "/auth/refresh-token",
            "/auth/confirm/**"
    };

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realmName)
                .grantType(OAuth2Constants.PASSWORD)
                .username(adminName)
                .password(adminPassword)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwtConfigurer -> jwtConfigurer
                        .jwtAuthenticationConverter(jwtProvider))
        );

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(SWAGGER_LIST).permitAll()
                .requestMatchers(AUTH_SERVICE_ENDPOINT).permitAll()
                .requestMatchers(USER_SERVICE_ENDPOINT).permitAll()
                .anyRequest().authenticated());

        http.oauth2Login(Customizer.withDefaults());

        http.logout(lOut -> lOut.logoutUrl("/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())));
        return http.build();
    }
}
