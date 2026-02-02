package com.example.erp.hr.dto;

import java.time.LocalDate;
import java.util.List;

public class EmployeeReportDTO {
    private String reportType;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<EmployeeDTO> employees;

    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public List<EmployeeDTO> getEmployees() { return employees; }
    public void setEmployees(List<EmployeeDTO> employees) { this.employees = employees; }
}

