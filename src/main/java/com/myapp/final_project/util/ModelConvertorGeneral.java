package com.myapp.final_project.util;

import com.myapp.final_project.entity.Company;
import com.myapp.final_project.entity.Department;
import com.myapp.final_project.entity.Employee;
import com.myapp.final_project.model.CompanyDTO;
import com.myapp.final_project.model.DepartmentDTO;
import com.myapp.final_project.model.EmployeeDTO;

import java.util.Optional;

public class ModelConvertorGeneral {

    private ModelConvertorGeneral() {

    }

    public static EmployeeDTO convertToEmployeeDTO(Employee employee) {
        DepartmentDTO departmentDTO = Optional.ofNullable(employee.getDepartment())
                .map(department ->
                        new DepartmentDTO(
                                department.getId(),
                                department.getName(),
                                department.getCompany() != null ? department.getCompany().getName() : null
                        ))
                .orElse(null);
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getEmail(),
                employee.getAge(), departmentDTO);
    }


    public static Employee convertToEmployee(EmployeeDTO employeeDTO) {
        return new Employee(employeeDTO.name(), employeeDTO.email(), employeeDTO.age());
    }

    public static DepartmentDTO convertToDepartmentDTO(Department department) {
        return Optional.ofNullable(department)
                .map(dep -> new DepartmentDTO(
                        dep.getId(),
                        dep.getName(),
                        dep.getCompany() != null ? dep.getCompany().getName() : null))
                .orElse(null);

    }

    public static Department convertToDepartment(DepartmentDTO departmentDTO, Company company) {
        Department department = new Department(departmentDTO.name());
        department.setCompany(company);
        return department;
    }

    public static Company convertToCompany(CompanyDTO companyDTO) {
        return new Company(companyDTO.name(), companyDTO.address());
    }

    public static CompanyDTO convertToCompanyDTO(Company company) {
        return Optional.ofNullable(company)
                .map(comp -> new CompanyDTO(comp.getId(), comp.getName(), comp.getAddress()))
                .orElse(null);
    }

}

