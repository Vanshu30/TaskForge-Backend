package com.Task_Forge.Microservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentDTO {

    @NotNull(message = "Company ID is required")
    private UUID companyId;

    @NotNull(message = "Amount is required")
    private double amount;
}
