package com.myapp.finalproject.service;

import com.myapp.finalproject.model.EmployeeDTO;

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
