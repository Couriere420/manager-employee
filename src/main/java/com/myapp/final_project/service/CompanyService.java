package com.myapp.final_project.service;

import com.myapp.final_project.model.CompanyDTO;
import com.myapp.final_project.model.DepartmentDTO;

import java.util.List;

public interface CompanyService {
    CompanyDTO createCompany(CompanyDTO companyDTO);

    CompanyDTO getCompanyById(Long id);

    List<DepartmentDTO> getAllDepartmentsFromCompany(String companyName);

    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);

    void deleteCompany(Long id);
}
