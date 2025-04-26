package com.Task_Forge.Microservice.Repository;

import com.Task_Forge.Microservice.Entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SprintRepository extends JpaRepository<Sprint, UUID> {
}
