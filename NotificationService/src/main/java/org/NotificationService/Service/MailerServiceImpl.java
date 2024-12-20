package org.NotificationService.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.NotificationService.Model.MailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailerServiceImpl implements MailerService {
    @Autowired
    JavaMailSender sender;



    @Override
    @Async
    public void send(MailInfo mail) throws MessagingException {
        // TODO Auto-generated method stub
        // Tạo message
        MimeMessage message = sender.createMimeMessage();
        // Sử dụng Helper để thiết lập các thông tin cần thiết cho message
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(), true);
        helper.setReplyTo(mail.getFrom());
//		String[] cc = mail.getCc();
//		if (cc != null && cc.length > 0) {
//			helper.setCc(cc);
//		}
//		String[] bcc = mail.getBcc();
//		if (bcc != null && bcc.length > 0) {
//			helper.setBcc(bcc);
//		}
//		MultipartFile[] attachments = mail.getAttachments();
//		System.out.println(attachments.length);
//		if (attachments != null && attachments.length > 0) {
//			for (int i = 0; i < attachments.length; i++) {
//				try {
//					helper.addAttachment(attachments[i].getOriginalFilename(),
//							new ByteArrayResource(attachments[i].getBytes()));
//				} catch (MessagingException | IOException e) {
//					System.out.println(e);
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//		}
        // Gửi message đến SMTP server
        sender.send(message);
    }

    @Override
    public void sendVerify(MailInfo mail) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        helper.setFrom(mail.getFrom());
        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getBody(), true);

        String[] cc = mail.getCc();
        if (cc != null && cc.length > 0) {
            helper.setCc(cc);
        }

        String[] bcc = mail.getBcc();
        if (bcc != null && bcc.length > 0) {
            helper.setBcc(bcc);
        }

        sender.send(message);
    }

    @Override
    public void send(String to, String subject, String body) throws MessagingException {
// TODO Auto-generated method stub
        try {
            this.send(new MailInfo(to, subject, body));
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    List<MailInfo> list = new ArrayList<>();

    @Override
    public void queue(MailInfo mail) {
        list.add(mail);
    }

    @Override
    public void queue(String to, String subject, String body) {
        queue(new MailInfo(to, subject, body));
    }

    @Scheduled(fixedDelay = 1)
    public void run() {
        while (!list.isEmpty()) {
            MailInfo mail = list.remove(0);
            try {
                this.send(mail);
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }


}
