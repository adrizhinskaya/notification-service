package org.example.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService;

    private final String to = "test@example.com";


    @Test
    void testSendWelcomeEmail() {
        emailService.sendWelcomeEmail(to);
        verify(emailSender, times(1)).send(welcomeEmail(SimpleMailMessage.class));
    }

    @Test
    void testSendGoodbyeEmail() {
        emailService.sendGoodbyeEmail(to);
        verify(emailSender, times(1)).send(byeEmail(SimpleMailMessage.class));
    }

    private SimpleMailMessage welcomeEmail(Class<SimpleMailMessage> simpleMailMessageClass) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_email@gmail.com");
        message.setTo(to);
        message.setSubject("Добро пожаловать!");
        message.setText("Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
        emailSender.send(message);
        return message;
    }

    private SimpleMailMessage byeEmail(Class<SimpleMailMessage> simpleMailMessageClass) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_email@gmail.com");
        message.setTo(to);
        message.setSubject("До свидания!");
        message.setText("Здравствуйте! Ваш аккаунт был удалён.");
        emailSender.send(message);
        return message;
    }

}
