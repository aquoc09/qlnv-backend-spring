package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.LeaveRecordRequest;
import com.kenji.qlnv_backend.dto.response.LeaveRecordResponse;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.LeaveRecord;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LeaveRecordService {
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public LeaveRecordResponse create(LeaveRecordRequest request);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public LeaveRecordResponse get(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<LeaveRecordResponse> getAll();

    List<LeaveRecordResponse> getAllByEmployee(Long empId);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public void delete(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public LeaveRecordResponse update(Long id, LeaveRecordRequest request);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<LeaveRecord> getAcceptedLeaveByMonth(
            Employee employee,
            int month,
            int year
    );
}
