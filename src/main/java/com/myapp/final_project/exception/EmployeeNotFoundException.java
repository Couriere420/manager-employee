package com.myapp.final_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmployeeNotFoundException extends ResponseStatusException {
    public EmployeeNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
