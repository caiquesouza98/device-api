package com.telecom.devicemanager.dto;

import com.telecom.devicemanager.model.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DeviceResponseDTO {
    private UUID id;
    private String name;
    private DeviceType type;
    private Instant createdAt;
}
