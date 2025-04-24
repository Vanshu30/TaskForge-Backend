package com.Task_Forge.Microservice.Repository;

import com.Task_Forge.Microservice.Entity.Company;
import com.Task_Forge.Microservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
//    User findByEmail(String email);
}
