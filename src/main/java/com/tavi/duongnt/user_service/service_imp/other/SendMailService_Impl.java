package com.tavi.duongnt.user_service.service_imp.other;


import com.tavi.duongnt.user_service.service.other.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SendMailService_Impl implements SendMailService {

    private static final Logger LOGGER = Logger.getLogger(SendMailService_Impl.class.getName());

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String emailSender;

    @Override
    public boolean sendMail(String userMail, String header, String content) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(userMail);
            mail.setFrom(emailSender);
            mail.setSubject(header);
            mail.setText(content);
            javaMailSender.send(mail);
            return true;
        } catch (MailException ex) {
            LOGGER.log(Level.SEVERE, "send-mail-error : {0}", ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean sendHtmlMail(String userMail, String header, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
            mail.setTo(userMail);
            mail.setFrom(emailSender);
            mail.setSubject(header);
            mail.setText(content, true);
            javaMailSender.send(mimeMessage);
            return true;
        } catch (MailException | MessagingException ex) {
            LOGGER.log(Level.SEVERE, "send-mail-error : {0}", ex.getMessage());
        }
        return false;
    }
}
