package fishinghelper.notification_service.messaging.producer;

import fishinghelper.notification_service.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageQueue(String message, String routingKey){
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME,routingKey,message);
    }
}
