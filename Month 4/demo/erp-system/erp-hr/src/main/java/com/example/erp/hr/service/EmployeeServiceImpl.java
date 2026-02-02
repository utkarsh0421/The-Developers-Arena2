package com.example.erp.hr.service;

import com.example.erp.hr.dto.CreateEmployeeRequest;
import com.example.erp.hr.dto.EmployeeDTO;
import com.example.erp.hr.dto.EmployeeReportDTO;
import com.example.erp.hr.model.Employee;
import com.example.erp.hr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(CreateEmployeeRequest request) {
        Employee e = new Employee();
        e.setFirstName(request.getFirstName());
        e.setLastName(request.getLastName());
        e.setEmail(request.getEmail());
        e.setDepartment(request.getDepartment());
        Employee saved = employeeRepository.save(e);
        return toDto(saved);
    }

    @Override
    public EmployeeDTO getEmployee(String id) {
        return employeeRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public EmployeeReportDTO generateReport(String reportType, LocalDate startDate, LocalDate endDate) {
        List<EmployeeDTO> dtos = employeeRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
        EmployeeReportDTO report = new EmployeeReportDTO();
        report.setReportType(reportType);
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        report.setEmployees(dtos);
        return report;
    }

    private EmployeeDTO toDto(Employee e) {
        EmployeeDTO d = new EmployeeDTO();
        d.setId(e.getId());
        d.setFirstName(e.getFirstName());
        d.setLastName(e.getLastName());
        d.setEmail(e.getEmail());
        d.setDepartment(e.getDepartment());
        return d;
    }
}

