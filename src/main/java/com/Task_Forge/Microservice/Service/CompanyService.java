package com.Task_Forge.Microservice.Service;

import com.Task_Forge.Microservice.DTO.CompanyDTO;
import com.Task_Forge.Microservice.Entity.Company;
import com.Task_Forge.Microservice.Entity.User;
import com.Task_Forge.Microservice.Exception.ResourceNotFoundException;
import com.Task_Forge.Microservice.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public Company createCompany(CompanyDTO companyDTO){

        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setDescription(companyDTO.getDescription());
        return companyRepository.save(company);
    }

    @Transactional(readOnly = true)
    public Company getCompanyById(UUID companyId){
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }
}
