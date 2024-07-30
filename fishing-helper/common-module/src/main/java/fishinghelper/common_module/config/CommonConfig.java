package fishinghelper.common_module.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "fishinghelper.common_module.dao")
@EntityScan(basePackages = "fishinghelper.common_module.entity")
@ComponentScan(basePackages ="fishinghelper.common_module" )
public class CommonConfig {
}
