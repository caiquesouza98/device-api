package com.telecom.devicemanager.controller;

import com.telecom.devicemanager.dto.DeviceRequestDTO;
import com.telecom.devicemanager.dto.DeviceResponseDTO;
import com.telecom.devicemanager.model.DeviceType;
import com.telecom.devicemanager.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(DeviceController.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeviceService deviceService;

    @Test
    void listDevices() throws Exception {
        List<DeviceResponseDTO> devices = List.of(
                new DeviceResponseDTO(UUID.randomUUID(), "iPhone", DeviceType.SMARTPHONE, Instant.now())
        );

        given(deviceService.listDevices()).willReturn(devices);

        mockMvc.perform(get("/devices").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("devices-list",
                        responseFields(
                                fieldWithPath("[].id").description("The device ID"),
                                fieldWithPath("[].name").description("The name of the device"),
                                fieldWithPath("[].type").description("Device type"),
                                fieldWithPath("[].createdAt").description("Creation timestamp in UTC")
                        )
                ));
    }

    @Test
    void createDevice() throws Exception {
        DeviceRequestDTO request = new DeviceRequestDTO("Pixel 6", DeviceType.SMARTPHONE);
        DeviceResponseDTO response = new DeviceResponseDTO(
                UUID.randomUUID(), request.getName(), request.getType(), Instant.now()
        );

        given(deviceService.createDevice(any())).willReturn(response);

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "Pixel 6",
                        "type": "SMARTPHONE"
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andDo(document("device-create",
                        requestFields(
                                fieldWithPath("name").description("Name of the device"),
                                fieldWithPath("type").description("Type of the device (e.g., SMARTPHONE, MODEM)")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Generated UUID for the device"),
                                fieldWithPath("name").description("Device name"),
                                fieldWithPath("type").description("Device type"),
                                fieldWithPath("createdAt").description("Timestamp when the device was created (ISO 8601)")
                        )
                ));
    }

    @Test
    void getDeviceById() throws Exception {
        UUID deviceId = UUID.randomUUID();
        DeviceResponseDTO response = new DeviceResponseDTO(
                deviceId, "iPhone 13", DeviceType.SMARTPHONE, Instant.now()
        );

        given(deviceService.getDeviceById(deviceId)).willReturn(response);

        mockMvc.perform(get("/devices/{id}", deviceId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deviceId.toString()))
                .andDo(document("device-get-by-id",
                        pathParameters(
                                parameterWithName("id").description("UUID of the device to fetch")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Device UUID"),
                                fieldWithPath("name").description("Device name"),
                                fieldWithPath("type").description("Device type"),
                                fieldWithPath("createdAt").description("Device creation timestamp (ISO 8601)")
                        )
                ));
    }
}
