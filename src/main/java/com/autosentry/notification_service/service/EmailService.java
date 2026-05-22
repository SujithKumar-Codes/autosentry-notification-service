package com.autosentry.notification_service.service;

import com.autosentry.notification_service.event.VehicleExpiryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendExpiryEmail(VehicleExpiryEvent event) {

        SimpleMailMessage message = new SimpleMailMessage();

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

        System.out.println("Email sent to " + event.getOwnerEmail());
    }
}