package fishinghelper.security_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(basePackages = {"fishinghelper.security_server.dao"})
public class RedisConfiguration {
    @Value("${host.name.redis}")
    private String hostNameRedis;

    @Value("${port.redis}")
    private int portRedis;


    @Bean
    public JedisConnectionFactory connectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration =new RedisStandaloneConfiguration();

        redisStandaloneConfiguration.setHostName(hostNameRedis);
        redisStandaloneConfiguration.setPort(portRedis);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> template = new RedisTemplate<>();

        template.setConnectionFactory(connectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }
}
