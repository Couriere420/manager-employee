package com.myapp.final_project.controller;

import com.myapp.final_project.model.CompanyDTO;
import com.myapp.final_project.model.DepartmentDTO;
import com.myapp.final_project.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Company", description = "APIs for managing Company")
@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Retrieve all departments associated with a given company.",
            description = "This endpoint fetches a list of all departments that belong to the specified company." +
                    " It is useful for clients who want to see all the departments within a specific company.")
    @GetMapping("/{companyName}/departments")
    public List<DepartmentDTO> findAllDepartmentsFromCompany(@PathVariable String companyName) {
        return companyService.getAllDepartmentsFromCompany(companyName);

    }

    @Operation(summary = "Find a company by its ID.", description = "This endpoint retrieves a company" +
            " based on its unique ID." +
            " It's useful for fetching details of a specific company using the ID as a parameter.")
    @GetMapping("/id")
    public CompanyDTO findCompanyById(Long id) {
        return companyService.getCompanyById(id);
    }

    @Operation(summary = "Create a new company.", description = "This endpoint allows the creation" +
            " of a new company" +
            " by providing the necessary details (e.g., company name, address) in the request body")
    @PostMapping
    public CompanyDTO createCompany(@RequestBody CompanyDTO companyDTO) {
        return companyService.createCompany(companyDTO);
    }

    @Operation(summary = "Update an existing company's details.", description = "This endpoint updates" +
            " the information" +
            " of an existing company identified by its ID. It allows modifying attributes like the name or address of the company.")
    @PutMapping("{id}")
    public CompanyDTO updateCompany(@RequestBody CompanyDTO companyDTO) {
        return companyService.updateCompany(companyDTO.id(), companyDTO);
    }

    @Operation(summary = "Delete a company by its ID.", description = "This endpoint deletes the company" +
            " identified by its ID. It’s useful for removing a company from the database.")
    @DeleteMapping("{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }

}
