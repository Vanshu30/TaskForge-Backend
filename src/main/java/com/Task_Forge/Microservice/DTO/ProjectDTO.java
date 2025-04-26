package com.Task_Forge.Microservice.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class ProjectDTO {

    @NotBlank(message = "Project name is required")
    private String name;

    private UUID companyId;
}
