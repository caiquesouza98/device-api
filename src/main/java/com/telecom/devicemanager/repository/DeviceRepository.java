package com.telecom.devicemanager.repository;

import com.telecom.devicemanager.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
}
