package com.telecom.devicemanager.controller;

import com.telecom.devicemanager.dto.DeviceRequestDTO;
import com.telecom.devicemanager.dto.DeviceResponseDTO;
import com.telecom.devicemanager.service.DeviceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @GetMapping
    public List<DeviceResponseDTO> listDevices() {
        return service.listDevices();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponseDTO createDevice(@Valid @RequestBody DeviceRequestDTO dto) {
        return service.createDevice(dto);
    }

    @GetMapping("/{id}")
    public DeviceResponseDTO getDevice(@PathVariable UUID id) {
        return service.getDeviceById(id);
    }
}
