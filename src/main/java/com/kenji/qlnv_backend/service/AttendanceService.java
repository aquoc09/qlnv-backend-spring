package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.AttendanceRequest;
import com.kenji.qlnv_backend.dto.response.AttendanceResponse;
import com.kenji.qlnv_backend.entity.Attendance;
import com.kenji.qlnv_backend.entity.Employee;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AttendanceService {
    public AttendanceResponse create(AttendanceRequest request);

    public AttendanceResponse get(Long id);

    public List<AttendanceResponse> getAll();

    @PreAuthorize("isAuthenticated()")
    public AttendanceResponse checkIn();

    @PreAuthorize("isAuthenticated()")
    public AttendanceResponse checkOut();

    public int getSumWorkingTimeOfEmployee(Long empId);

    public int getCountCheckInLateOfEmployee(Long empId);

    public List<AttendanceResponse> findAllByEmployee(Long empId);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<AttendanceResponse> findAllByDate(LocalDate date);

    public List<Attendance> findAllByEmployeeAndMonthYear(Employee employee, int month, int year);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public void delete(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public AttendanceResponse update(Long id, AttendanceRequest request);
}
