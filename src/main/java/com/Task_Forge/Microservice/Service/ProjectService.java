package com.Task_Forge.Microservice.Service;

import com.Task_Forge.Microservice.DTO.ProjectDTO;
import com.Task_Forge.Microservice.Entity.Company;
import com.Task_Forge.Microservice.Entity.Project;
import com.Task_Forge.Microservice.Exception.ResourceNotFoundException;
import com.Task_Forge.Microservice.Repository.CompanyRepository;
import com.Task_Forge.Microservice.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public Project createProject(ProjectDTO projectDTO, UUID companyId){
        Company company = companyRepository.findById(companyId)
                .orElseThrow(()-> new ResourceNotFoundException("Company not found"));

        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setCompany(company);

        return projectRepository.save(project);
    }

    public Project getProjectById(UUID projectId){
        return projectRepository.findById(projectId)
                .orElseThrow(()-> new ResourceNotFoundException("Project not found"));
    }
}
