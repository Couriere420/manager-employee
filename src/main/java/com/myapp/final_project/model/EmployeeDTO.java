package com.myapp.final_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myapp.final_project.exception.InvalidInputException;

public record EmployeeDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY) Long id,
                          String name,
                          String email,
                          Integer age,
                          DepartmentDTO departmentDTO) {
    @Override
    public Integer age() {
        if (age < 0)
            throw new InvalidInputException("Age cannot be less than zero");
        return age;
    }
}
