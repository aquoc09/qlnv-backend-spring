package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.AttendanceRequest;
import com.kenji.qlnv_backend.dto.response.AttendanceResponse;
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

    public List<AttendanceResponse> findAllByDate(LocalDate date);

    public void delete(Long id);

    public AttendanceResponse update(Long id, AttendanceRequest request);
}
