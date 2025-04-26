package com.Task_Forge.Microservice.Repository;

import com.Task_Forge.Microservice.Entity.Role;
import com.Task_Forge.Microservice.Exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Role findByName(String name);

    default Role getRoleByName(String name){
        Role role = findByName(name);
        if (role == null){
            throw new ResourceNotFoundException("Role not found: " + name);
        }
        return role;
    }
}
