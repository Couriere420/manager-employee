package com.myapp.final_project.service.Impl;

import com.myapp.final_project.entity.Company;
import com.myapp.final_project.entity.Department;
import com.myapp.final_project.entity.Employee;
import com.myapp.final_project.exception.CompanyNotFoundException;
import com.myapp.final_project.exception.DepartmentNotFoundException;
import com.myapp.final_project.exception.EmployeeNotFoundException;
import com.myapp.final_project.model.DepartmentDTO;
import com.myapp.final_project.model.EmployeeDTO;
import com.myapp.final_project.repository.CompanyRepository;
import com.myapp.final_project.repository.DepartmentRepository;
import com.myapp.final_project.repository.EmployeeRepository;
import com.myapp.final_project.service.EmployeeService;
import com.myapp.final_project.util.ModelConvertorGeneral;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }


    @Override
    public EmployeeDTO getEmployeeById(long id) {
        Employee foundEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("I can't find employee with id: " + id));
        return ModelConvertorGeneral.convertToEmployeeDTO(foundEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> allEmployees = employeeRepository.findAll();
        return allEmployees
                .stream()
                .map(ModelConvertorGeneral::convertToEmployeeDTO)
                .toList();
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {

        Employee employee = ModelConvertorGeneral.convertToEmployee(employeeDTO);

        DepartmentDTO depDTO = employeeDTO.departmentDTO();
        Department department = departmentRepository.findByName(depDTO.name())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found: " + depDTO.name()));
        if (depDTO.companyName() != null && department.getCompany() == null) {
            Company company = companyRepository.findByName(depDTO.companyName())
                    .orElseThrow(() -> new CompanyNotFoundException("Company not found: " + depDTO.companyName()));
            department.setCompany(company);
            departmentRepository.save(department);
        }

        employee.setDepartment(department);
        Employee createdEmployee = employeeRepository.save(employee);
        return ModelConvertorGeneral.convertToEmployeeDTO(createdEmployee);
    }

    @Override
    public EmployeeDTO updateEmployee(long id, EmployeeDTO employeeDTO) {
        Employee employee = ModelConvertorGeneral.convertToEmployee(employeeDTO);
        employee.setId(id);
        Department department = departmentRepository.findByName(employeeDTO.departmentDTO().name())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
        employee.setDepartment(department);
        Employee updateEmployee = employeeRepository.save(employee);
        return ModelConvertorGeneral.convertToEmployeeDTO(updateEmployee);
    }

    public EmployeeDTO assignEmployeeToDepartment(Long employeeId, String departmentName) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("I can't find employee with id: "
                        + employeeId));

        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with name: "
                        + departmentName));

        employee.setDepartment(department);

        Employee updateEmployee = employeeRepository.save(employee);

        return ModelConvertorGeneral.convertToEmployeeDTO(updateEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployeesByDepartment(String departmentName) {
        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with name: " + departmentName));
        List<Employee> employees = employeeRepository.findByDepartment(department);
        return employees
                .stream()
                .map(ModelConvertorGeneral::convertToEmployeeDTO)
                .toList();
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);

    }

}
