package com.myapp.finalproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CompanyDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                         Long id,
                         String name,
                         String address) {
}
