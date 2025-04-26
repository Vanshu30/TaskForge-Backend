package com.Task_Forge.Microservice.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import javax.crypto.Mac;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SprintDTO {

    @NotBlank(message = "Sprint status is required")
    private String status; //Active, Completed

    private UUID projectId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
