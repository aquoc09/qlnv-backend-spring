package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.entity.Attendance;
import com.kenji.qlnv_backend.entity.Contract;
import com.kenji.qlnv_backend.entity.Department;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.LeaveRecord;
import com.kenji.qlnv_backend.entity.Salary;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.repository.AttendanceRepository;
import com.kenji.qlnv_backend.repository.ContractRepository;
import com.kenji.qlnv_backend.repository.DepartmentRepository;
import com.kenji.qlnv_backend.repository.EmployeeRepository;
import com.kenji.qlnv_backend.repository.LeaveRecordRepository;
import com.kenji.qlnv_backend.repository.SalaryRepository;
import com.kenji.qlnv_backend.service.ReportService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportServiceImp implements ReportService {
    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;
    SalaryRepository salaryRepository;
    AttendanceRepository attendanceRepository;
    LeaveRecordRepository leaveRecordRepository;
    ContractRepository contractRepository;

    @Override
    public ByteArrayInputStream exportAllEmployeesToExcel() throws IOException {
        List<Employee> employees = employeeRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Department");
        header.createCell(3).setCellValue("Email");
        header.createCell(4).setCellValue("Phone");
        header.createCell(5).setCellValue("Position");

        int rowIdx = 1;
        for (Employee e : employees) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(e.getId() != null ? e.getId() : 0);
            row.createCell(1).setCellValue(e.getFullName() != null ? e.getFullName() : "");
            row.createCell(2).setCellValue(
                    e.getDepartment() != null && e.getDepartment().getName() != null
                            ? e.getDepartment().getName()
                            : ""
            );
            row.createCell(3).setCellValue(e.getEmail() != null ? e.getEmail() : "");
            row.createCell(4).setCellValue(e.getPhone() != null ? e.getPhone() : "");
            row.createCell(5).setCellValue(e.getPosition() != null ? e.getPosition() : "");
        }

        for (int i = 0; i <= 5; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream exportAllDepartmentsToExcel() throws IOException {
        List<Department> departments = departmentRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Departments");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Manager");

        int rowIdx = 1;
        for (Department d : departments) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(d.getId() != null ? d.getId() : 0);
            row.createCell(1).setCellValue(d.getName() != null ? d.getName() : "");
            row.createCell(2).setCellValue(
                    d.getManager() != null && d.getManager().getFullName() != null
                            ? d.getManager().getFullName()
                            : ""
            );
        }

        for (int i = 0; i <= 2; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream exportEmployeesByDepartmentToExcel(Long depId) throws IOException {
        Department department = departmentRepository.findById(depId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));

        List<Employee> employees = employeeRepository.findByDepartment(department);
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees by Department");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Department");
        header.createCell(3).setCellValue("Email");
        header.createCell(4).setCellValue("Phone");
        header.createCell(5).setCellValue("Position");

        int rowIdx = 1;
        for (Employee e : employees) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(e.getId() != null ? e.getId() : 0);
            row.createCell(1).setCellValue(e.getFullName() != null ? e.getFullName() : "");
            row.createCell(2).setCellValue(
                    e.getDepartment() != null && e.getDepartment().getName() != null
                            ? e.getDepartment().getName()
                            : ""
            );
            row.createCell(3).setCellValue(e.getEmail() != null ? e.getEmail() : "");
            row.createCell(4).setCellValue(e.getPhone() != null ? e.getPhone() : "");
            row.createCell(5).setCellValue(e.getPosition() != null ? e.getPosition() : "");
        }

        for (int i = 0; i <= 5; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream exportSalaryByYearMonthToExcel(int month, int year) throws IOException {
        List<Salary> salaries = salaryRepository.findAllByMonthAndYear(month, year);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Salary_" + month + "_" + year);

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Salary ID");
        header.createCell(1).setCellValue("Employee ID");
        header.createCell(2).setCellValue("Employee Name");
        header.createCell(3).setCellValue("Month");
        header.createCell(4).setCellValue("Year");
        header.createCell(5).setCellValue("Base Salary");
        header.createCell(6).setCellValue("Allowance");
        header.createCell(7).setCellValue("Bonus");
        header.createCell(8).setCellValue("Deduction");
        header.createCell(9).setCellValue("Net Salary");
        header.createCell(10).setCellValue("Payment Date");
        header.createCell(11).setCellValue("Status");

        int rowIdx = 1;
        for (Salary s : salaries) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(s.getId() != null ? s.getId() : 0);
            row.createCell(1).setCellValue(
                    s.getEmployee() != null && s.getEmployee().getId() != null ? s.getEmployee().getId() : 0
            );
            row.createCell(2).setCellValue(
                    s.getEmployee() != null && s.getEmployee().getFullName() != null ? s.getEmployee().getFullName() : ""
            );
            row.createCell(3).setCellValue(s.getMonth() != null ? s.getMonth() : 0);
            row.createCell(4).setCellValue(s.getYear() != null ? s.getYear() : 0);
            row.createCell(5).setCellValue(s.getBaseSalary() != null ? s.getBaseSalary().doubleValue() : 0);
            row.createCell(6).setCellValue(s.getAllowance() != null ? s.getAllowance().doubleValue() : 0);
            row.createCell(7).setCellValue(s.getBonus() != null ? s.getBonus().doubleValue() : 0);
            row.createCell(8).setCellValue(s.getDeduction() != null ? s.getDeduction().doubleValue() : 0);
            row.createCell(9).setCellValue(s.getNetSalary() != null ? s.getNetSalary().doubleValue() : 0);
            row.createCell(10).setCellValue(s.getPaymentDate() != null ? s.getPaymentDate().toString() : "");
            row.createCell(11).setCellValue(s.getStatus() != null ? s.getStatus().name() : "");
        }

        for (int i = 0; i <= 11; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream exportAttendanceByYearMonthToExcel(int month, int year) throws IOException {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<Attendance> attendances = attendanceRepository.findAllByWorkDateBetween(startDate, endDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance_" + month + "_" + year);

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Attendance ID");
        header.createCell(1).setCellValue("Employee ID");
        header.createCell(2).setCellValue("Employee Name");
        header.createCell(3).setCellValue("Work Date");
        header.createCell(4).setCellValue("Check In");
        header.createCell(5).setCellValue("Check Out");
        header.createCell(6).setCellValue("Status");
        header.createCell(7).setCellValue("Checked Out");
        header.createCell(8).setCellValue("Time Worked");

        int rowIdx = 1;
        for (Attendance a : attendances) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(a.getId() != null ? a.getId() : 0);
            row.createCell(1).setCellValue(
                    a.getEmployee() != null && a.getEmployee().getId() != null ? a.getEmployee().getId() : 0
            );
            row.createCell(2).setCellValue(
                    a.getEmployee() != null && a.getEmployee().getFullName() != null ? a.getEmployee().getFullName() : ""
            );
            row.createCell(3).setCellValue(a.getWorkDate() != null ? a.getWorkDate().toString() : "");
            row.createCell(4).setCellValue(a.getCheckIn() != null ? a.getCheckIn().toString() : "");
            row.createCell(5).setCellValue(a.getCheckOut() != null ? a.getCheckOut().toString() : "");
            row.createCell(6).setCellValue(a.getStatus() != null ? a.getStatus().name() : "");
            row.createCell(7).setCellValue(a.isCheckedOut() ? "Yes" : "No");
            row.createCell(8).setCellValue(a.getTimeWorked());
        }

        for (int i = 0; i <= 8; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream exportLeaveRecordByYearMonthToExcel(int month, int year) throws IOException {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        List<LeaveRecord> leaveRecords = leaveRecordRepository.findAllByDateRange(startDate, endDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Leave_Record_" + month + "_" + year);

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Leave Record ID");
        header.createCell(1).setCellValue("Employee ID");
        header.createCell(2).setCellValue("Employee Name");
        header.createCell(3).setCellValue("Start Date");
        header.createCell(4).setCellValue("End Date");
        header.createCell(5).setCellValue("Reason");
        header.createCell(6).setCellValue("Status");

        int rowIdx = 1;
        for (LeaveRecord lr : leaveRecords) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(lr.getId() != null ? lr.getId() : 0);
            row.createCell(1).setCellValue(
                    lr.getEmployee() != null && lr.getEmployee().getId() != null ? lr.getEmployee().getId() : 0
            );
            row.createCell(2).setCellValue(
                    lr.getEmployee() != null && lr.getEmployee().getFullName() != null ? lr.getEmployee().getFullName() : ""
            );
            row.createCell(3).setCellValue(lr.getStartDate() != null ? lr.getStartDate().toString() : "");
            row.createCell(4).setCellValue(lr.getEndDate() != null ? lr.getEndDate().toString() : "");
            row.createCell(5).setCellValue(lr.getReason() != null ? lr.getReason() : "");
            row.createCell(6).setCellValue(lr.getStatus() != null ? lr.getStatus().name() : "");
        }

        for (int i = 0; i <= 7; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public ByteArrayInputStream exportContractByEmployeeToPdf(Long empId) throws IOException {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));

        Contract contract = contractRepository.findByEmployee(employee)
                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_EXISTED));

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph title = new Paragraph(
                    "CONTRACT INFORMATION",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)
            );
            title.setSpacingAfter(15f);
            document.add(title);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);

            table.addCell("Contract ID");
            table.addCell(String.valueOf(contract.getId()));

            table.addCell("Employee ID");
            table.addCell(String.valueOf(employee.getId()));

            table.addCell("Employee Name");
            table.addCell(employee.getFullName() != null ? employee.getFullName() : "");

            table.addCell("Contract Number");
            table.addCell(contract.getContractNumber() != null ? contract.getContractNumber() : "");

            table.addCell("Contract Type");
            table.addCell(contract.getContractType() != null ? contract.getContractType().name() : "");

            table.addCell("Contract Level");
            table.addCell(contract.getContractLevel() != null ? contract.getContractLevel().name() : "");

            table.addCell("Start Date");
            table.addCell(contract.getStartDate() != null ? contract.getStartDate().toString() : "");

            table.addCell("End Date");
            table.addCell(contract.getEndDate() != null ? contract.getEndDate().toString() : "");

            table.addCell("Base Salary");
            table.addCell(contract.getBaseSalary() != null ? contract.getBaseSalary().toString() : "0");

            table.addCell("Status");
            table.addCell(contract.getStatus() != null ? contract.getStatus().name() : "");

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new IOException("Failed to export contract PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}