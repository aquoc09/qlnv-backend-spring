package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.LeaveBalanceRequest;
import com.kenji.qlnv_backend.dto.response.LeaveBalanceResponse;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.LeaveBalance;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LeaveBalanceService {
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    LeaveBalanceResponse create(LeaveBalanceRequest request);

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    LeaveBalanceResponse get(Long id);

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    List<LeaveBalanceResponse> getAll();

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    void delete(Long id);

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    LeaveBalanceResponse update(Long id, LeaveBalanceRequest request);

    void resetYearlyLeaveBalance();

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    LeaveBalance getLeaveBalanceByEmployeeAndYear(Long empId, int year);

    LeaveBalanceResponse getByEmployee(Long empId);
}