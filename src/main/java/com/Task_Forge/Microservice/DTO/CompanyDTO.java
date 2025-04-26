package com.Task_Forge.Microservice.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyDTO {

    @NotBlank(message = "Company name is required")
    private String name;

    @NotBlank(message = "Company description is required")
    private String description;
}
