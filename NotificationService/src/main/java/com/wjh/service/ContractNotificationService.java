package com.wjh.service;

import com.wjh.event.OrderPlacedEvent;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ContractNotificationService {

    private final MailSenderService mailSenderService;

    @KafkaListener(topics = "order-placed")
    public void depositeContractNotification(OrderPlacedEvent orderPlacedEvent) throws MessagingException {
        log.info("Received order");
        this.mailSenderService
                .sendDepositeContractEmail(
                        orderPlacedEvent.getEmail(),
                        orderPlacedEvent.getEncodedContractPdf(),
                        orderPlacedEvent.getName(),
                        orderPlacedEvent.getContractId());
    }
}
