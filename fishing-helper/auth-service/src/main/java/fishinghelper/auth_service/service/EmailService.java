package fishinghelper.auth_service.service;

import fishinghelper.common_module.entity.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends an email message to the specified user.
     *
     * @param user    the recipient user
     * @param subject the subject of the email
     * @param body    the body content of the email
     */
    public void sendMessage(User user,String subject,String body){
        SimpleMailMessage message=new SimpleMailMessage();

        message.setTo(user.getMail());
        message.setFrom("a3310010752@mail.ru");
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
        log.info("send message to "+ user.getMail());
    }
}
