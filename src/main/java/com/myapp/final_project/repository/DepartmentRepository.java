package com.myapp.final_project.repository;

import com.myapp.final_project.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface DepartmentRepository extends JpaRepository<Department, Long> {


    Optional<Department> findByName(String name);

    List<Department> findByCompany_Name(String companyName);
}
