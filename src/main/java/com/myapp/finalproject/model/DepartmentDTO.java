package com.myapp.finalproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public record DepartmentDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                            Long id,
                            String name,
                            String companyName) {
}
