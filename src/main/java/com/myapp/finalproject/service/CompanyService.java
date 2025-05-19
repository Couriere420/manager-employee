package com.myapp.finalproject.service;

import com.myapp.finalproject.model.CompanyDTO;
import com.myapp.finalproject.model.DepartmentDTO;

import java.util.List;

public interface CompanyService {
    CompanyDTO createCompany(CompanyDTO companyDTO);

    CompanyDTO getCompanyById(Long id);

    List<CompanyDTO> getAllCompanies();

    List<DepartmentDTO> getAllDepartmentsFromCompany(String companyName);

    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);

    void deleteCompany(Long id);
}
