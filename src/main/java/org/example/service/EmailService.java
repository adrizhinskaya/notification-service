package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendWelcomeEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_email@gmail.com");
        message.setTo(to);
        message.setSubject("Добро пожаловать!");
        message.setText("Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
        emailSender.send(message);
    }

    public void sendGoodbyeEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_email@gmail.com");
        message.setTo(to);
        message.setSubject("До свидания!");
        message.setText("Здравствуйте! Ваш аккаунт был удалён.");
        emailSender.send(message);
    }
}
