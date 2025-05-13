package com.myapp.final_project.service.Impl;

import com.myapp.final_project.entity.Department;
import com.myapp.final_project.entity.Employee;
import com.myapp.final_project.exception.CompanyNotFoundException;
import com.myapp.final_project.exception.EmployeeNotFoundException;
import com.myapp.final_project.model.DepartmentDTO;
import com.myapp.final_project.model.EmployeeDTO;
import com.myapp.final_project.repository.CompanyRepository;
import com.myapp.final_project.repository.DepartmentRepository;
import com.myapp.final_project.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    void getEmployeeById() {
        Employee employee = new Employee(NAME, EMAIL, AGE, DEPARTMENT);
        when(employeeRepository.findById(ID)).thenReturn(Optional.of(employee));
        EmployeeDTO employeeDTO = employeeServiceImpl.getEmployeeById(ID);
        assertNotNull(employeeDTO);
        assertEquals(NAME, employeeDTO.name());
    }

    @Test
    void testThrowException() {

        assertThrows(EmployeeNotFoundException.class, () -> employeeServiceImpl.getEmployeeById(2L));

    }

    @Test
    void getAllEmployees() {

        Employee employee1 = new Employee(NAME, EMAIL, AGE, DEPARTMENT);
        Employee employee2 = new Employee("Alex", "alex@mail.com", 30, DEPARTMENT);
        when(employeeRepository.findAll()).thenReturn(List.of(employee1, employee2));
        List<EmployeeDTO> employeeDTOList = employeeServiceImpl.getAllEmployees();
        assertEquals(2, employeeDTOList.size());

        assertEquals(NAME, employeeDTOList.getFirst().name());
        assertEquals(EMAIL, employeeDTOList.get(0).email());
        assertEquals(AGE, employeeDTOList.get(0).age());

        assertEquals("Alex", employeeDTOList.get(1).name());
        assertEquals("alex@mail.com", employeeDTOList.get(1).email());
        assertEquals(30, employeeDTOList.get(1).age());
    }

    @Test
    void getAllEmployees_largeList() {

        List<Employee> largeEmployeeList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeEmployeeList.add(new Employee("Employee " + i, "email" + i + "@example.com",
                    20 + i, DEPARTMENT));
        }

        when(employeeRepository.findAll()).thenReturn(largeEmployeeList);

        List<EmployeeDTO> employeeDTOList = employeeServiceImpl.getAllEmployees();
        assertEquals(1000, employeeDTOList.size());
    }

    @Test
    void createEmployee_withExistingDepartment_noCompany() {

        DepartmentDTO departmentDTO = new DepartmentDTO(1L, "IT", null); // Fără companie
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "Gabriel",
                "gabriel@mail.com", 30, departmentDTO);

        Department department = new Department("IT");
        when(departmentRepository.findByName("IT")).thenReturn(Optional.of(department));
        when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(new Employee("Gabriel",
                "gabriel@mail.com", 30, department));

        EmployeeDTO result = employeeServiceImpl.createEmployee(employeeDTO);


        assertNotNull(result);
        assertEquals("Gabriel", result.name());
        assertEquals("IT", result.departmentDTO().name());
    }

    @Test
    void createEmployee_throwsCompanyNotFoundException() {
        DepartmentDTO departmentDTO = new DepartmentDTO(1L, "IT", "NonExistentCompany");
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "Jane Smith", "jane@example.com", 28, departmentDTO);

        Department department = new Department("IT"); // departament fără companie

        when(departmentRepository.findByName("IT")).thenReturn(Optional.of(department));
        when(companyRepository.findByName("NonExistentCompany")).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class, () -> employeeServiceImpl.createEmployee(employeeDTO));
    }
}
