package com.autosentry.notification_service.service;

import com.autosentry.notification_service.event.VehicleExpiryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@autosentry.local}")
    private String fromAddress;

    public void sendExpiryEmail(VehicleExpiryEvent event) {

        log.info("Preparing to construct {} email for Vehicle Plate: {} to {}", event.getEventType(), event.getPlateNumber(), event.getOwnerEmail());

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromAddress.isBlank() ? "noreply@autosentry.local" : fromAddress);
        message.setTo(event.getOwnerEmail());

        message.setSubject("Vehicle Expiry Alert");

        String body = """
                Dear User,

                Your vehicle with plate number %s has a %s in %d days.

                Please renew it before expiry.

                Regards,
                AutoSentry
                """.formatted(
                event.getPlateNumber(),
                event.getEventType().replace("_", " ").toLowerCase(),
                event.getDaysLeft()
        );

        message.setText(body);

        log.debug("Email payload constructed successfully. Attempting to dispatch via JavaMailSender.");

        try {
            mailSender.send(message);
            log.info("Successfully dispatched expiry email to {} for plate {}", event.getOwnerEmail(), event.getPlateNumber());
        } catch (Exception e) {
            // Log the critical failure if MailHog or the SMTP server is down
            log.error("Failed to send expiry email to {} for plate {}. Error: {}", event.getOwnerEmail(), event.getPlateNumber(), e.getMessage(), e);

            // Re-throw the exception so the Kafka consumer knows the email failed and can retry it
            throw e;
        }
    }
}