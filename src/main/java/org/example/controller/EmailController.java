package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;

import org.example.model.EmailRequest;
import org.example.model.enums.OperationType;
import org.example.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    @Operation(summary = "Отправка нового письма")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        try {
            if (OperationType.CREATE.equals(request.getOperation())) {
                emailService.sendWelcomeEmail(request.getEmail());
            } else if (OperationType.DELETE.equals(request.getOperation())) {
                emailService.sendGoodbyeEmail(request.getEmail());
            }
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
    }
}
