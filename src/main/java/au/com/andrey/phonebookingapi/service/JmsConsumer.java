package au.com.andrey.phonebookingapi.service;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * This JMS listener for testing purposes only.
 * It prints messages received in the JSM queue to the console.
 */
@Service
@Slf4j
public class JmsConsumer {
    @JmsListener(destination = "${application.phone-booking-queue}")
    public void processMessage(Message message) throws JMSException {
        if (message instanceof TextMessage m) {
            log.info("### New Message Received ###\n:{}", m.getText());
        }
    }
}
