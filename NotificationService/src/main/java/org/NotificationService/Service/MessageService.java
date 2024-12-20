package org.NotificationService.Service;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.NotificationService.Model.MailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageService {
    @Autowired
    MailerService mailerService;

    @KafkaListener(id = "notificationGroup", topics = "notification")
    public void listen(MailInfo mailInfo) throws MessagingException {
        log.info("Received MessageDTO: {}", mailInfo.getTo());

//        mailerService.sendVerify(mailInfo);
    }
}
