package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmailController.class)
class EmailControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void sendEmail_createOperation() throws Exception {
        EmailRequest request = new EmailRequest("CREATE", "test@example.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Email sent successfully!"));

        Mockito.verify(emailService, Mockito.times(1)).sendWelcomeEmail("test@example.com");
    }

    @Test
    void sendEmail_deleteOperation() throws Exception {
        EmailRequest request = new EmailRequest("DELETE", "test@example.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Email sent successfully!"));

        Mockito.verify(emailService, Mockito.times(1)).sendGoodbyeEmail("test@example.com");
    }

    static class EmailRequest {
        String operation;
        String email;

        public EmailRequest(String operation, String email) {
            this.operation = operation;
            this.email = email;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
