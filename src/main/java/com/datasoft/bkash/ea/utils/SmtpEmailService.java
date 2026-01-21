package com.datasoft.bkash.ea.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Slf4j
@Component
@EnableAsync
public class SmtpEmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String mailUserName;

    @Async
    public void sendEmail(String subject, String body, String[] toEmails, String[] toCC, String[] toBCC) throws Exception {

        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true, CharEncoding.UTF_8);
        helper.setFrom(mailUserName);
        helper.setSubject(subject);
        helper.setTo(toEmails);
        helper.setText(body, true);
        log.info("Email Subject: " + subject);
        log.info("To emails: " + toEmails);
        javaMailSender.send(mail);
        log.info("Email send successfully!");
    }
}
