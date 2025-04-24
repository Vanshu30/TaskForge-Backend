package com.Task_Forge.Microservice.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentDTO {

    @NotBlank(message = "Message is required")
    private String message;

    private UUID taskId;
    private UUID userId;
}
