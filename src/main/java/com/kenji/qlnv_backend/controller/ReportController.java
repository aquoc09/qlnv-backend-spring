package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.service.ReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/reports")
public class ReportController {
    ReportService reportService;

    @GetMapping("/employees/excel")
    public ResponseEntity<InputStreamResource> exportAllEmployeesToExcel() throws IOException {
        InputStreamResource file = new InputStreamResource(reportService.exportAllEmployeesToExcel());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .body(file);
    }

    @GetMapping("/departments/excel")
    public ResponseEntity<InputStreamResource> exportAllDepartmentsToExcel() throws IOException {
        InputStreamResource file = new InputStreamResource(reportService.exportAllDepartmentsToExcel());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=departments.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .body(file);
    }

    @GetMapping("/departments/{depId}/employees/excel")
    public ResponseEntity<InputStreamResource> exportEmployeesByDepartmentToExcel(@PathVariable Long depId)
            throws IOException {
        InputStreamResource file = new InputStreamResource(reportService.exportEmployeesByDepartmentToExcel(depId));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_department_" + depId + ".xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .body(file);
    }

    @GetMapping("/salaries/excel")
    public ResponseEntity<InputStreamResource> exportSalaryByYearMonthToExcel(
            @RequestParam int month,
            @RequestParam int year
    ) throws IOException {
        InputStreamResource file = new InputStreamResource(
                reportService.exportSalaryByYearMonthToExcel(month, year)
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=salaries_" + month + "_" + year + ".xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .body(file);
    }

    @GetMapping("/attendances/excel")
    public ResponseEntity<InputStreamResource> exportAttendanceByYearMonthToExcel(
            @RequestParam int month,
            @RequestParam int year
    ) throws IOException {
        InputStreamResource file = new InputStreamResource(
                reportService.exportAttendanceByYearMonthToExcel(month, year)
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attendances_" + month + "_" + year + ".xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .body(file);
    }

    @GetMapping("/leave-records/excel")
    public ResponseEntity<InputStreamResource> exportLeaveRecordByYearMonthToExcel(
            @RequestParam int month,
            @RequestParam int year
    ) throws IOException {
        InputStreamResource file = new InputStreamResource(
                reportService.exportLeaveRecordByYearMonthToExcel(month, year)
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=leave_records_" + month + "_" + year + ".xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .body(file);
    }

    @GetMapping("/contracts/{empId}/pdf")
    public ResponseEntity<InputStreamResource> exportContractByEmployeeToPdf(@PathVariable Long empId)
            throws IOException {
        InputStreamResource file = new InputStreamResource(reportService.exportContractByEmployeeToPdf(empId));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contract_employee_" + empId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }
}