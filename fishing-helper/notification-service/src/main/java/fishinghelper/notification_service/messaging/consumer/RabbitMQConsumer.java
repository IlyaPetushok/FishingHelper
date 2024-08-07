package fishinghelper.notification_service.messaging.consumer;

import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.notification_service.config.RabbitConfig;
import fishinghelper.notification_service.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RabbitMQConsumer {
    private final UserRepositories userRepositories;
    private final EmailService emailService;

    @Autowired
    public RabbitMQConsumer(UserRepositories userRepositories,EmailService emailService) {
        this.userRepositories = userRepositories;
        this.emailService=emailService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Received notification: " + message);
        List<String> usersEmails=userRepositories.findUsersByMailStatus(true).stream()
                .map(User::getMail)
                .collect(Collectors.toList());

        emailService.sendMessageToUsers(usersEmails,message.split(":")[0],message.split(":")[1]);
    }
}
