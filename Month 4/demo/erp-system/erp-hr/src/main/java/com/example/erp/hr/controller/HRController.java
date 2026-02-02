package com.example.erp.hr.controller;

import com.example.erp.hr.dto.CreateEmployeeRequest;
import com.example.erp.hr.dto.EmployeeDTO;
import com.example.erp.hr.dto.EmployeeReportDTO;
import com.example.erp.hr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/hr")
@Validated
public class HRController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('MANAGER')")
    public EmployeeDTO createEmployee(@RequestBody CreateEmployeeRequest request) {
        return employeeService.createEmployee(request);
    }

    @GetMapping("/employees/{id}")
    @PreAuthorize("hasRole('HR_ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYEE')")
    public EmployeeDTO getEmployee(@PathVariable String id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/employees/reports")
    @Cacheable(value = "employeeReports", key = "#reportType")
    public EmployeeReportDTO getEmployeeReport(@RequestParam String reportType,
                                               @RequestParam(required = false) LocalDate startDate,
                                               @RequestParam(required = false) LocalDate endDate) {
        return employeeService.generateReport(reportType, startDate, endDate);
    }
}

