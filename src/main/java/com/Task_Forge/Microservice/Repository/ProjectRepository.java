package com.Task_Forge.Microservice.Repository;

import com.Task_Forge.Microservice.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
