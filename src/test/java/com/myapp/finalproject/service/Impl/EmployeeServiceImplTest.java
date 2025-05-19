package com.myapp.finalproject.service.Impl;

import com.myapp.finalproject.entity.Company;
import com.myapp.finalproject.entity.Department;
import com.myapp.finalproject.entity.Employee;
import com.myapp.finalproject.exception.CompanyNotFoundException;
import com.myapp.finalproject.exception.DepartmentNotFoundException;
import com.myapp.finalproject.exception.EmployeeNotFoundException;
import com.myapp.finalproject.model.DepartmentDTO;
import com.myapp.finalproject.model.EmployeeDTO;
import com.myapp.finalproject.repository.CompanyRepository;
import com.myapp.finalproject.repository.DepartmentRepository;
import com.myapp.finalproject.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    private static final long ID = 1;
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final int AGE = 12;
    private static final Department DEPARTMENT = new Department("name");
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private CompanyRepository companyRepository;

    private EmployeeServiceImpl employeeServiceImpl;

    @BeforeEach
    void setUp() {
        employeeServiceImpl = new EmployeeServiceImpl(employeeRepository, departmentRepository,
                companyRepository);

    }

    @Test
    void getEmployeeById_returnsCorrectData() {
        Employee employee = new Employee(NAME, EMAIL, AGE, DEPARTMENT);
        when(employeeRepository.findById(ID)).thenReturn(Optional.of(employee));

        EmployeeDTO employeeDTO = employeeServiceImpl.getEmployeeById(ID);

        assertNotNull(employeeDTO);
        assertEquals(NAME, employeeDTO.name());
        assertEquals(EMAIL, employeeDTO.email());
        assertEquals(AGE, employeeDTO.age());

        assertNotNull(employeeDTO.departmentDTO());
        assertEquals(DEPARTMENT.getName(), employeeDTO.departmentDTO().name());
    }


    @Test
    void getEmployeeById_ShouldThrowException_WhenEmployeeNotFound() {
        long invalidId = 999L;
        when(employeeRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () ->
                employeeServiceImpl.getEmployeeById(invalidId));
    }

    @Test
    void getAllEmployees_shouldReturnListOfEmployeeDTOs() {
        Employee employee1 = new Employee("Name", "email", 12, new Department("IT"));
        Employee employee2 = new Employee("Ion", "ion@mail.com", 33, new Department("HR"));

        when(employeeRepository.findAll()).thenReturn(List.of(employee1, employee2));

        List<EmployeeDTO> result = employeeServiceImpl.getAllEmployees();

        assertEquals(2, result.size());
        assertEquals("Name", result.get(0).name());
        assertEquals("Ion", result.get(1).name());
    }

    @Test
    void createEmployee_ShouldReturnEmployeeDTO_WhenDepartmentHasCompany() {

        Company company = new Company();
        company.setId(ID);
        company.setName("CompanyX");

        Department department = new Department();
        department.setId(ID);
        department.setName("IT");
        department.setCompany(company);

        DepartmentDTO departmentDTO = new DepartmentDTO(1L, "IT",
                "CompanyX");
        EmployeeDTO inputDTO = new EmployeeDTO(null, NAME, EMAIL, AGE, departmentDTO);

        Employee savedEmployee = new Employee();
        savedEmployee.setId(ID);
        savedEmployee.setName(NAME);
        savedEmployee.setEmail(EMAIL);
        savedEmployee.setAge(AGE);
        savedEmployee.setDepartment(department);

        DepartmentDTO expectedDepartmentDTO = new DepartmentDTO(1L, "IT",
                "CompanyX");
        EmployeeDTO expectedDTO = new EmployeeDTO(ID, NAME, EMAIL, AGE, expectedDepartmentDTO);

        when(departmentRepository.findByName("IT")).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        EmployeeDTO result = employeeServiceImpl.createEmployee(inputDTO);

        assertEquals(expectedDTO.id(), result.id());
        assertEquals(expectedDTO.name(), result.name());
        assertEquals(expectedDTO.email(), result.email());
        assertEquals(expectedDTO.age(), result.age());
        assertEquals(expectedDTO.departmentDTO().name(), result.departmentDTO().name());
        assertEquals(company.getName(), result.departmentDTO().companyName());
    }

    @Test
    void createEmployee_throwsCompanyNotFoundException() {
        DepartmentDTO departmentDTO = new DepartmentDTO(1L, "IT", "NonExistentCompany");
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "Ghita", "ghita@mail.com",
                28, departmentDTO);

        Department department = new Department("IT");

        when(departmentRepository.findByName("IT")).thenReturn(Optional.of(department));
        when(companyRepository.findByName("NonExistentCompany")).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class, () -> employeeServiceImpl.createEmployee(employeeDTO));
    }

    @Test
    void updateEmployee_success() {

        long employeeId = 1L;
        Department department = new Department("IT");
        department.setId(10L);

        DepartmentDTO departmentDTO = new DepartmentDTO(10L, "IT", null);
        EmployeeDTO employeeDTO = new EmployeeDTO(null, "UpdatedName", "updated@mail.com",
                31, departmentDTO);

        Employee updatedEmployee = new Employee("UpdatedName", "updated@mail.com", 31);
        updatedEmployee.setId(employeeId);
        updatedEmployee.setDepartment(department);

        when(departmentRepository.findByName("IT")).thenReturn(Optional.of(department));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        EmployeeDTO result = employeeServiceImpl.updateEmployee(employeeId, employeeDTO);

        assertEquals("UpdatedName", result.name());
        assertEquals("updated@mail.com", result.email());
        assertEquals(31, result.age());
        assertEquals("IT", result.departmentDTO().name());

        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void updateEmployee_departmentNotFound() {

        long employeeId = 2L;

        DepartmentDTO departmentDTO = new DepartmentDTO(null, "NonExistentDept", null);
        EmployeeDTO employeeDTO = new EmployeeDTO(null, "Ana", "ana@mail.com",
                25, departmentDTO);

        when(departmentRepository.findByName("NonExistentDept")).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class, () -> {
            employeeServiceImpl.updateEmployee(employeeId, employeeDTO);
        });

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_shouldDeleteSuccessfully() {
        long employeeId = 1L;

        employeeServiceImpl.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}