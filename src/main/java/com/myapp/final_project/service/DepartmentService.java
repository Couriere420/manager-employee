package com.myapp.final_project.service;

import com.myapp.final_project.model.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);

    void deleteDepartment(Long id);
}
