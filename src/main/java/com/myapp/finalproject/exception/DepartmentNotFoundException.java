package com.myapp.finalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DepartmentNotFoundException extends ResponseStatusException {
    public DepartmentNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
