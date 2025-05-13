package com.myapp.final_project.service;

import com.myapp.final_project.model.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO getEmployeeById(long id);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(long id, EmployeeDTO employeeDTO);

    EmployeeDTO assignEmployeeToDepartment(Long userId, String departmentName);

    List<EmployeeDTO> getAllEmployeesByDepartment(String department);

    void deleteEmployee(long id);


}
