package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        String[] parts = message.substring(1, message.length() - 1).split(", ");
        String operation = parts[0].split("=")[1];
        String email = parts[1].split("=")[1];

        if ("CREATE".equals(operation)) {
            emailService.sendWelcomeEmail(email);
        } else if ("DELETE".equals(operation)) {
            emailService.sendGoodbyeEmail(email);
        }
    }
}
