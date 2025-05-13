package com.myapp.final_project.controller;

import com.myapp.final_project.model.EmployeeDTO;
import com.myapp.final_project.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employees Management", description = "APIs for managing employees in the system")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get employee details by ID", description = """
            Retrieves employee information\
             based on the provided employee ID\
            . Returns a EmployeeDTO object containing employee details such as name,email and age.""")
    @GetMapping("{id}")
    public EmployeeDTO findEmployee(@PathVariable long id) {
        return employeeService.getEmployeeById(id);

    }

    @Operation(summary = "Find all employees", description = """
            Fetches a list of all employees \
            from the database and returns their details as a list of EmployeeDTO objects.
            
            """)
    @GetMapping
    public List<EmployeeDTO> findAllEmployees() {
        return employeeService.getAllEmployees();

    }

    @Operation(summary = "Find employees by department", description = """
            Retrieves all employees assigned to the specified department \
            based on department ID.
            """)
    @GetMapping("/by-department/{departmentName}")
    public List<EmployeeDTO> findEmployeeByDepartment(@PathVariable String departmentName) {
        return employeeService.getAllEmployeesByDepartment(departmentName);
    }

    @Operation(summary = "Create a new employee", description = """
            Adds a new employee to the system and returns\
             the created employee details.""")
    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @Operation(summary = "Update an existing employee", description = """
            Updates a employee's details based on the given ID.\
             \
            If the employee exists, their information is modified and returned.\
             If the employee is not found, an appropriate error response is returned.""")
    @PutMapping("{id}")
    public EmployeeDTO updateEmployee(@PathVariable long id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO);
    }

    @Operation(summary = "Assign a employee to a department", description = """
            This endpoint allows you to assign a employee to a specific department
             using their respective IDs.""")
    @PutMapping("/{employeeId}/department/{departmentName}")
    public EmployeeDTO assignEmployeeToDepartment(@PathVariable Long employeeId,
                                                  @PathVariable String departmentName) {
        return employeeService.assignEmployeeToDepartment(employeeId, departmentName);
    }

    @Operation(summary = "Remove a employee by ID", description = """
            Removes a employee from the system \
            based on the provided employee ID. If the employee does not exist, \
            an appropriate error response is returned.""")
    @DeleteMapping("{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
    }
}