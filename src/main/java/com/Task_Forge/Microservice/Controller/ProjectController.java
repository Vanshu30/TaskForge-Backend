package com.Task_Forge.Microservice.Controller;

import com.Task_Forge.Microservice.DTO.ProjectDTO;
import com.Task_Forge.Microservice.Entity.Project;
import com.Task_Forge.Microservice.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PreAuthorize("hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO projectDTO){
        return ResponseEntity.ok(projectService.createProject(projectDTO, projectDTO.getCompanyId()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable UUID id){
        return ResponseEntity.ok(projectService.getProjectById(id));
    }
}
