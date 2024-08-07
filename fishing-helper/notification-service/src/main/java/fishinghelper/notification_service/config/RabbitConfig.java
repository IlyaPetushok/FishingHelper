package fishinghelper.notification_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "fishinghelper.common_module")
public class RabbitConfig {

    @Value("${host.rabbit}")
    private String hostRabbit;

    @Value("${port.rabbit}")
    private Integer portRabbit;

    public static final String QUEUE_NAME_3 = "not_spr_queue_3";
    public static final String QUEUE_NAME_2 = "not_spr_queue_2";
    public static final String QUEUE_NAME_1 = "not_spr_queue";
    public static final String EXCHANGE_NAME = "not_spr_exch";
    public static final String ROUTING_KEY_1 = "spr_123";
    public static final String ROUTING_KEY_2 = "spr_123_2";
    public static final String ROUTING_KEY_3 = "spr_123_3";


    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME_1, true);
    }

    @Bean
    public Queue queue2(){
        return new Queue(QUEUE_NAME_2,true);
    }

    @Bean
    public Queue queue3(){
        return new Queue(QUEUE_NAME_3,true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_1);
    }

    @Bean
    public Binding binding2(Queue queue2,DirectExchange exchange){
        return BindingBuilder.bind(queue2).to(exchange).with(ROUTING_KEY_2);
    }

    @Bean
    public Binding binding3(Queue queue3,DirectExchange exchange){
        return BindingBuilder.bind(queue3).to(exchange).with(ROUTING_KEY_3);
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostRabbit);
        connectionFactory.setPort(portRabbit);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }
}
