package com.telecom.devicemanager.dto;

import com.telecom.devicemanager.model.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class DeviceRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    private DeviceType type;
}
