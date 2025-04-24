package com.Task_Forge.Microservice.Repository;

import com.Task_Forge.Microservice.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
