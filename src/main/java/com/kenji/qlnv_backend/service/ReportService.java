package com.kenji.qlnv_backend.service;

import org.springframework.security.access.prepost.PreAuthorize;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface ReportService {
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ByteArrayInputStream exportAllEmployeesToExcel() throws IOException;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ByteArrayInputStream exportAllDepartmentsToExcel() throws IOException;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ByteArrayInputStream exportEmployeesByDepartmentToExcel(Long depId) throws IOException;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ByteArrayInputStream exportSalaryByYearMonthToExcel(int month, int year) throws IOException;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ByteArrayInputStream exportAttendanceByYearMonthToExcel(int month, int year) throws IOException;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ByteArrayInputStream exportLeaveRecordByYearMonthToExcel(int month, int year) throws IOException;

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    ByteArrayInputStream exportContractByEmployeeToPdf(Long empId) throws IOException;
}