package com.myapp.finalproject.util;

import com.myapp.finalproject.entity.Company;
import com.myapp.finalproject.entity.Department;
import com.myapp.finalproject.entity.Employee;
import com.myapp.finalproject.model.CompanyDTO;
import com.myapp.finalproject.model.DepartmentDTO;
import com.myapp.finalproject.model.EmployeeDTO;

import java.util.Optional;

public class ModelConvertorGeneral {

    private ModelConvertorGeneral() {

    }

    public static EmployeeDTO convertToEmployeeDTO(Employee employee) {
        DepartmentDTO departmentDTO = Optional.ofNullable(employee.getDepartment())
                .map(ModelConvertorGeneral::convertToDepartmentDTO)
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

    public static CompanyDTO convertToCompanyDTO(Company company) {
        return Optional.ofNullable(company)
                .map(comp -> new CompanyDTO(comp.getId(), comp.getName(), comp.getAddress()))
                .orElse(null);
    }

    public static Company convertToCompany(CompanyDTO companyDTO) {
        return new Company(companyDTO.name(), companyDTO.address());
    }
}

