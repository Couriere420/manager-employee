package com.myapp.finalproject.service.Impl;

import com.myapp.finalproject.entity.Company;
import com.myapp.finalproject.entity.Department;
import com.myapp.finalproject.exception.CompanyNotFoundException;
import com.myapp.finalproject.model.CompanyDTO;
import com.myapp.finalproject.model.DepartmentDTO;
import com.myapp.finalproject.repository.CompanyRepository;
import com.myapp.finalproject.repository.DepartmentRepository;
import com.myapp.finalproject.service.CompanyService;
import com.myapp.finalproject.util.ModelConvertorGeneral;
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

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with ID: " + id));
        return ModelConvertorGeneral.convertToCompanyDTO(company);
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        List<Company> companyList = companyRepository.findAll();
        return companyList
                .stream()
                .map(ModelConvertorGeneral::convertToCompanyDTO)
                .toList();
    }

    @Override
    public List<DepartmentDTO> getAllDepartmentsFromCompany(String companyName) {
        List<Department> departments = departmentRepository.findByCompany_Name(companyName);

        if (departments.isEmpty()) {
            throw new CompanyNotFoundException("Company not found or has no departments: " + companyName);
        }

        return departments
                .stream()
                .map(ModelConvertorGeneral::convertToDepartmentDTO)
                .toList();
    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with ID: " + id));

        existingCompany.setName(companyDTO.name());
        existingCompany.setAddress(companyDTO.address());

        Company updatedCompany = companyRepository.save(existingCompany);

        return ModelConvertorGeneral.convertToCompanyDTO(updatedCompany);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);

    }
}
