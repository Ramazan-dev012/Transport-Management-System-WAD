package com.example.transportsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String smtpUsername;

    @Value("${spring.mail.password:}")
    private String smtpPassword;

    @Value("${app.mail.from:}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        // Если SMTP не настроен, лучше сразу объяснить, а не "тихо" не отправлять
        if (smtpUsername == null || smtpUsername.isBlank() || smtpPassword == null || smtpPassword.isBlank()) {
            throw new IllegalStateException("SMTP не настроен: заполните spring.mail.username и spring.mail.password (лучше через переменные окружения MAIL_USERNAME/MAIL_PASSWORD)");
        }

        log.info("Отправка email: to={}, subject={} (from={})", to, subject, (from == null || from.isBlank()) ? "<default>" : from);

        SimpleMailMessage message = new SimpleMailMessage();
        if (from != null && !from.isBlank()) {
            message.setFrom(from);
        }
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        log.info("Email успешно отправлен: to={}", to);
    }
}
