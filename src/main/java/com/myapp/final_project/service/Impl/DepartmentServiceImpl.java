package com.myapp.final_project.service.Impl;

import com.myapp.final_project.entity.Company;
import com.myapp.final_project.entity.Department;
import com.myapp.final_project.exception.CompanyNotFoundException;
import com.myapp.final_project.exception.DepartmentNotFoundException;
import com.myapp.final_project.model.DepartmentDTO;
import com.myapp.final_project.repository.CompanyRepository;
import com.myapp.final_project.repository.DepartmentRepository;
import com.myapp.final_project.service.DepartmentService;
import com.myapp.final_project.util.ModelConvertorGeneral;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Company company = companyRepository.findByName(departmentDTO.companyName())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found: " + departmentDTO.companyName()));

        Department department = ModelConvertorGeneral.convertToDepartment(departmentDTO, company);
        Department savedDepartment = departmentRepository.save(department);
        return ModelConvertorGeneral.convertToDepartmentDTO(savedDepartment);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));
        return ModelConvertorGeneral.convertToDepartmentDTO(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(ModelConvertorGeneral::convertToDepartmentDTO)
                .toList();
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with ID: " + id));

        department.setName(departmentDTO.name());

        Company company = companyRepository.findByName(departmentDTO.companyName())
                .orElseThrow(() -> new CompanyNotFoundException("Company not found with name: " + departmentDTO.companyName()));

        department.setCompany(company);

        Department updatedDepartment = departmentRepository.save(department);
        return ModelConvertorGeneral.convertToDepartmentDTO(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);

    }

}
