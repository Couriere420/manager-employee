package com.myapp.final_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.myapp.final_project.entity.Company;

public record DepartmentDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                            Long id,
                            String name,
                            String companyName) {
}
