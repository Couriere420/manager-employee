package com.myapp.finalproject.service.Impl;

import com.myapp.finalproject.entity.Company;
import com.myapp.finalproject.entity.Department;
import com.myapp.finalproject.entity.Employee;
import com.myapp.finalproject.exception.CompanyNotFoundException;
import com.myapp.finalproject.exception.DepartmentNotFoundException;
import com.myapp.finalproject.exception.EmployeeNotFoundException;
import com.myapp.finalproject.model.DepartmentDTO;
import com.myapp.finalproject.model.EmployeeDTO;
import com.myapp.finalproject.repository.CompanyRepository;
import com.myapp.finalproject.repository.DepartmentRepository;
import com.myapp.finalproject.repository.EmployeeRepository;
import com.myapp.finalproject.service.EmployeeService;
import com.myapp.finalproject.util.ModelConvertorGeneral;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;

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
        if (department.getCompany() == null) {
            throw new IllegalStateException("Department " + depDTO.name() + " has no associated company.");
        }
        employee.setDepartment(department);
        Employee createdEmployee = employeeRepository.save(employee);
        return ModelConvertorGeneral.convertToEmployeeDTO(createdEmployee);
    }

    @Override
    public EmployeeDTO updateEmployee(long id, EmployeeDTO employeeDTO) {
        employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        Department department = departmentRepository.findByName(employeeDTO.departmentDTO().name())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with name: "
                        + employeeDTO.departmentDTO().name()));
        if (department.getCompany() == null) {
            throw new IllegalStateException("Department " + department.getName() + " has no associated company.");
        }
        Employee employee = ModelConvertorGeneral.convertToEmployee(employeeDTO);
        employee.setId(id);
        employee.setDepartment(department);
        Employee updatedEmployee = employeeRepository.save(employee);
        return ModelConvertorGeneral.convertToEmployeeDTO(updatedEmployee);
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
