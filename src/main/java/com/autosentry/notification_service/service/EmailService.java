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

        mailSender.send(message);

        log.info("Expiry email sent to {} for plate {}", event.getOwnerEmail(), event.getPlateNumber());
    }
}