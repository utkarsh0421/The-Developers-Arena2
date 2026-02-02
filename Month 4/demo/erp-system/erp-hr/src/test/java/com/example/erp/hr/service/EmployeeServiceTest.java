package com.example.erp.hr.service;

import com.example.erp.hr.dto.CreateEmployeeRequest;
import com.example.erp.hr.dto.EmployeeDTO;
import com.example.erp.hr.model.Employee;
import com.example.erp.hr.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private com.example.erp.hr.service.EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEmployee() {
        CreateEmployeeRequest req = new CreateEmployeeRequest();
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setEmail("john.doe@example.com");
        req.setDepartment("Engineering");

        Employee saved = new Employee();
        saved.setId("1");
        saved.setFirstName("John");
        saved.setLastName("Doe");
        saved.setEmail("john.doe@example.com");
        saved.setDepartment("Engineering");

        when(employeeRepository.save(any(Employee.class))).thenReturn(saved);

        EmployeeDTO dto = employeeService.createEmployee(req);
        assertNotNull(dto);
        assertEquals("1", dto.getId());
        assertEquals("John Doe", dto.getFullName());
    }

    @Test
    public void testGetEmployee() {
        Employee existing = new Employee();
        existing.setId("2");
        existing.setFirstName("Jane");
        existing.setLastName("Smith");
        existing.setEmail("jane.smith@example.com");
        existing.setDepartment("HR");

        when(employeeRepository.findById("2")).thenReturn(Optional.of(existing));

        EmployeeDTO dto = employeeService.getEmployee("2");
        assertNotNull(dto);
        assertEquals("2", dto.getId());
        assertEquals("Jane Smith", dto.getFullName());
    }
}

