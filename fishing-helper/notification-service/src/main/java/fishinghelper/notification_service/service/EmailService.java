package fishinghelper.notification_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

//    @Async
    public void sendMessageToUsers(List<String> userEmails,String subject,String body){
        for (String mail : userEmails){
            sendMessage(mail,subject,body);
        }
    }

    /**
     * Sends an email message to the specified user.
     *
     * @param mail    the mail witch yse user
     * @param subject the subject of the email
     * @param body    the body content of the email
     */
    public void sendMessage(String mail,String subject,String body){
        SimpleMailMessage message=new SimpleMailMessage();

        message.setTo(mail);
        message.setFrom("a3310010752@mail.ru");
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
        log.info("send message to "+ mail);
    }
}
