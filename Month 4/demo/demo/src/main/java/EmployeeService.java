package com.example.erp.hr.service;

import com.example.erp.hr.dto.CreateEmployeeRequest;
import com.example.erp.hr.dto.EmployeeDTO;
import com.example.erp.hr.dto.EmployeeReportDTO;

import java.time.LocalDate;

public interface EmployeeService {
    EmployeeDTO createEmployee(CreateEmployeeRequest request);
    EmployeeDTO getEmployee(String id);
    EmployeeReportDTO generateReport(String reportType, LocalDate startDate, LocalDate endDate);
}

