package fishinghelper.notification_service.messaging.consumer;

import fishinghelper.common_module.dao.UserRepositories;
import fishinghelper.common_module.entity.user.User;
import fishinghelper.notification_service.config.RabbitConfig;
import fishinghelper.notification_service.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailSendException;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RabbitMQConsumer {
    @Value("url.confirm.email")
    private String confirmUrlEmail;

    private final UserRepositories userRepositories;
    private final EmailService emailService;

    @Autowired
    public RabbitMQConsumer(UserRepositories userRepositories,EmailService emailService) {
        this.userRepositories = userRepositories;
        this.emailService=emailService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_1)
    public void receiveMessageNotificationImportantArticle(String message) {
        log.info("Received notification important article: " + message);
        List<String> usersEmails=userRepositories.findUsersByMailStatus(true).stream()
                .map(User::getMail)
                .collect(Collectors.toList());

        emailService.sendMessageToUsers(usersEmails,message.split(":")[0],message.split(":")[1]);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_2)
    public void receiveMessageNotificationApprovedEmail(String message) {
        log.info("Received notification:" + message);
        User user = userRepositories.findUserByMail(message);

        String encodedEmail = Base64.getEncoder().encodeToString(user.getMail().getBytes(StandardCharsets.UTF_8));
        String body="Dear "+user.getName()+"!!!\n"
                +"Please confirm your mail for FishingHelpers\n"
                +confirmUrlEmail
                + encodedEmail;

        try {
            emailService.sendMessage(message,"Confirm Email",body);
        }catch (MailSendException e) {
            log.error(e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME_3)
    public void receiveMessageNotificationUpdatePassword(String message) {
        log.info("Received notification: " + message);
        emailService.sendMessageHtml(message,"Request Update Password",readFile());
    }


    /**
     * Reads the content of an HTML file to use in an email message.
     *
     * @return the content of the HTML file as a string
     */
    private String readFile() {
        try {
            Resource resource = new ClassPathResource("/htmlForEmail.html");
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the HTML file", e);
        }
    }
}
