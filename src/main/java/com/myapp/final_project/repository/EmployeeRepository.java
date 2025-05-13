package com.myapp.final_project.repository;

import com.myapp.final_project.entity.Department;
import com.myapp.final_project.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(Department department);
}
