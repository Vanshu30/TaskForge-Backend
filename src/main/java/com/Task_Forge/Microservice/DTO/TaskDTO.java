package com.Task_Forge.Microservice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskDTO {

    @NotBlank(message = "Task title is required")
    private String title;

    @Size(max = 1000, message = "Description can't exceed 1000 characters")
    private String description;

    @NotBlank(message = "Task status is required")
    private String status;

    @NotNull(message = "Project ID is required")
    private UUID projectId;

    @NotNull(message = "Assigned user ID is required")
    private UUID assignedTo;
}
