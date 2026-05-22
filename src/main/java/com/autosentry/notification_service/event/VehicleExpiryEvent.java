package com.autosentry.notification_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleExpiryEvent {
    private Long vehicleId;
    private Long ownerId;
    private String ownerEmail;
    private String plateNumber;
    private String eventType;
    private long daysLeft;
}