package com.telecom.devicemanager.service;

import com.telecom.devicemanager.dto.DeviceRequestDTO;
import com.telecom.devicemanager.dto.DeviceResponseDTO;
import com.telecom.devicemanager.exception.NotFoundException;
import com.telecom.devicemanager.model.Device;
import com.telecom.devicemanager.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DeviceService {

    private final DeviceRepository repository;

    public DeviceService(DeviceRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public DeviceResponseDTO createDevice(DeviceRequestDTO dto) {
        log.info("Creating device...");
        Device device = new Device();
        device.setName(dto.getName());
        device.setType(dto.getType());
        Device saved = repository.save(device);
        return new DeviceResponseDTO(saved.getId(), saved.getName(), saved.getType(), saved.getCreatedAt());
    }

    public List<DeviceResponseDTO> listDevices() {
        log.info("Fetching all devices...");
        return repository.findAll().stream()
                .map(d -> new DeviceResponseDTO(d.getId(), d.getName(), d.getType(), d.getCreatedAt()))
                .toList();
    }

    public DeviceResponseDTO getDeviceById(UUID id) {
        log.info("Fetching device {} ...", id);
        Device d = repository.findById(id).orElseThrow(() -> new NotFoundException("Device Not Found"));
        return new DeviceResponseDTO(d.getId(), d.getName(), d.getType(), d.getCreatedAt());
    }
}
