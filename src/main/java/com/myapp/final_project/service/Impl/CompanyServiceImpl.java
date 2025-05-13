package com.myapp.final_project.service.Impl;

import com.myapp.final_project.entity.Company;
import com.myapp.final_project.entity.Department;
import com.myapp.final_project.exception.CompanyNotFoundException;
import com.myapp.final_project.model.CompanyDTO;
import com.myapp.final_project.model.DepartmentDTO;
import com.myapp.final_project.repository.CompanyRepository;
import com.myapp.final_project.repository.DepartmentRepository;
import com.myapp.final_project.service.CompanyService;
import com.myapp.final_project.util.ModelConvertorGeneral;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, DepartmentRepository departmentRepository) {
        this.companyRepository = companyRepository;
        this.departmentRepository = departmentRepository;
    }


    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {

        Company company = ModelConvertorGeneral.convertToCompany(companyDTO);
        Company savedCompany = companyRepository.save(company);
        return ModelConvertorGeneral.convertToCompanyDTO(savedCompany);
    }

    @Override
    public CompanyDTO getCompanyById(Long id) {

        Company company = companyRepository.findById(id).orElseThrow();
        return ModelConvertorGeneral.convertToCompanyDTO(company);
    }

    @Override
    public List<DepartmentDTO> getAllDepartmentsFromCompany(String companyName) {

        companyRepository.findByName(companyName)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found: " + companyName));

        List<Department> departments = departmentRepository.findByCompany_Name(companyName);

        return departments.stream()
                .map(ModelConvertorGeneral::convertToDepartmentDTO)
                .toList();
    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        return null;
    }

    @Override
    public void deleteCompany(Long id) {

    }
}
