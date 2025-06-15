import org.example.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = "user-events")
@DirtiesContext
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "spring.mail.host=localhost",
        "spring.mail.port=2525",
        "spring.mail.username=test@example.com",
        "spring.mail.password=password",
        "spring.mail.properties.mail.smtp.auth=false",
        "spring.mail.properties.mail.smtp.starttls.enable=false"
})
public class KafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    public void testUserCreationEvent() throws Exception {
        String email = "test@example.com";
        String createMessage = "UserEvent{operation='CREATE', email='" + email + "'}";

        kafkaTemplate.send("user-events", email, createMessage);
        kafkaTemplate.flush();

        verify(javaMailSender, timeout(2000)).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testUserDeletionEvent() throws Exception {
        String email = "test2@example.com";
        String deleteMessage = "UserEvent{operation='DELETE', email='" + email + "'}";

        kafkaTemplate.send("user-events", email, deleteMessage);
        kafkaTemplate.flush();

        verify(javaMailSender, timeout(2000)).send(any(SimpleMailMessage.class));
    }
}

