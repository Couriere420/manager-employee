package com.myapp.finalproject.controller;


import com.myapp.finalproject.model.DepartmentDTO;
import com.myapp.finalproject.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Departments Management", description = "APIs for managing departments in the system")
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @Operation(summary = "Create a new department", description = """
            Adds a new department to the system and returns the created department details.""")
    @PostMapping
    public DepartmentDTO createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

    @Operation(summary = "Get department details by ID", description = """
            Retrieves department information based on the provided department ID.
            Returns a DepartmentDTO object containing department details like name.""")
    @GetMapping("{id}")
    public DepartmentDTO findDepartment(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @Operation(summary = "Retrieve all departments", description = """
            Fetches a list of all departments from the database and returns their details as a list of DepartmentDTO objects.""")
    @GetMapping
    public List<DepartmentDTO> findAllDepartments() {
        return departmentService.getAllDepartments();
    }


    @Operation(summary = "Update an existing department", description = """
            Updates a department’s details based on the given ID.
            If the department exists, its information is modified and returned.
            If the department is not found, an appropriate error response is returned.""")
    @PutMapping("{id}")
    public DepartmentDTO updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        return departmentService.updateDepartment(id, departmentDTO);
    }

    @Operation(summary = "Remove a department by ID", description = """
            Removes a department from the system based on the provided department ID.
            If the department does not exist, an appropriate error response is returned.""")
    @DeleteMapping("{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}

