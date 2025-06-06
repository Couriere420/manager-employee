package com.myapp.finalproject.repository;

import com.myapp.finalproject.entity.Department;
import com.myapp.finalproject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(Department department);
}
