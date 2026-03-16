package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.LeaveBalanceRequest;
import com.kenji.qlnv_backend.dto.response.LeaveBalanceResponse;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.LeaveBalance;

import java.util.List;

public interface LeaveBalanceService {
    LeaveBalanceResponse create(LeaveBalanceRequest request);

    LeaveBalanceResponse get(Long id);

    List<LeaveBalanceResponse> getAll();

    void delete(Long id);

    LeaveBalanceResponse update(Long id, LeaveBalanceRequest request);

    void resetYearlyLeaveBalance();

    LeaveBalance getLeaveBalanceByEmployeeAndYear(Long empId, int year);

    LeaveBalanceResponse getByEmployee(Long empId);
}