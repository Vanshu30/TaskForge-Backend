package com.Task_Forge.Microservice.Repository;

import com.Task_Forge.Microservice.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
