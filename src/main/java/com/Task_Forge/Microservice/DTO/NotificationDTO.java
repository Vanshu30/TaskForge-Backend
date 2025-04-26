package com.Task_Forge.Microservice.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationDTO {

    @NotBlank(message = "Message is required")
    private String message;

    private String type; //INFO, WARNING

    private UUID userId;
}
