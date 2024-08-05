package fishinghelper.security_server.config;


import fishinghelper.security_server.util.JwtFilter;
import fishinghelper.security_server.util.JwtProvider;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true)
@ComponentScan(basePackages = {"fishinghelper.common_module"})
public class SecurityConfiguration {
//    private final JwtFilter jwtFilter;
//    private final LogoutHandler logoutHandler;

//    @Bean
//    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter(){
//        return new JwtGrantedAuthoritiesConverter();
//    }

    private final JwtProvider jwtProvider;

    @Autowired
    public SecurityConfiguration(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    private static final String[] SWAGGER_LIST={
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-resources"
    };

    private static final String[] AUTH_SERVICE_ENDPOINT={
            "/auth/registration",
            "/auth/authorization",
            "/confirm/**"
    };

//    @Autowired
//    public SecurityConfiguration(JwtFilter jwtFilter, LogoutHandler logoutHandler) {
//        this.jwtFilter = jwtFilter;
//        this.logoutHandler = logoutHandler;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtProvider))
        );
        http.oauth2Login(Customizer.withDefaults());
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(SWAGGER_LIST).permitAll()
                        .requestMatchers(AUTH_SERVICE_ENDPOINT).permitAll()
                        .anyRequest().authenticated());
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        http.logout(lOut -> {
//            lOut.logoutUrl("/logout")
//                    .addLogoutHandler(logoutHandler)
//                    .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()));
//        });
        return http.build();
    }

//    @Bean
//    Collection generateAuthoritiesFromClaim(Collection roles) {
//        return (Collection) roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(
//                Collectors.toList());
//    }
}
