package com.Task_Forge.Microservice.Controller;

import com.Task_Forge.Microservice.DTO.CompanyDTO;
import com.Task_Forge.Microservice.Entity.Company;
import com.Task_Forge.Microservice.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    //    @PreAuthorize("hasRole('ADMIN')") //only admins can create companies
    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDTO companyDTO){
        return ResponseEntity.ok(companyService.createCompany(companyDTO));
    }

    //    @PreAuthorize("isAuthenticated()") //any logged in user can view company
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable UUID id){
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

}