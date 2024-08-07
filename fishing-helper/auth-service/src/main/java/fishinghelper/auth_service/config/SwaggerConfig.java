package fishinghelper.auth_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    GroupedOpenApi groupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("auth-service")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("AUTH-SERVICE").version("V1"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}
