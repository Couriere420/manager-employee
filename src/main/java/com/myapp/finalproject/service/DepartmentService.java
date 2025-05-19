package com.myapp.finalproject.service;

import com.myapp.finalproject.model.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);

    void deleteDepartment(Long id);
}
