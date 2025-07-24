package com.telecom.devicemanager.controller.service;

import com.telecom.devicemanager.dto.DeviceRequestDTO;
import com.telecom.devicemanager.dto.DeviceResponseDTO;
import com.telecom.devicemanager.model.Device;
import com.telecom.devicemanager.model.DeviceType;
import com.telecom.devicemanager.repository.DeviceRepository;
import com.telecom.devicemanager.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @Test
    void shouldCreateDeviceSuccessfully() {
        DeviceRequestDTO request = new DeviceRequestDTO("iPhone", DeviceType.SMARTPHONE);
        Device device = new Device(UUID.randomUUID(), request.getName(), request.getType(), Instant.now());

        Mockito.when(deviceRepository.save(Mockito.any(Device.class))).thenReturn(device);

        DeviceResponseDTO response = deviceService.createDevice(request);

        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getType(), response.getType());
    }

    @Test
    void shouldReturnListOfDevices() {
        List<Device> mockDevices = List.of(
                new Device(UUID.randomUUID(), "Modem", DeviceType.MODEM, Instant.now()),
                new Device(UUID.randomUUID(), "Router", DeviceType.ROUTER, Instant.now())
        );

        Mockito.when(deviceRepository.findAll()).thenReturn(mockDevices);

        List<DeviceResponseDTO> devices = deviceService.listDevices();

        assertEquals(2, devices.size());
    }
}
