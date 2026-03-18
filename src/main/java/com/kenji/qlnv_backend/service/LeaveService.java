package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.LeaveRequest;
import com.kenji.qlnv_backend.dto.response.LeaveResponse;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.LeaveRecord;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LeaveService {
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public LeaveResponse create(LeaveRequest request);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public LeaveResponse get(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<LeaveResponse> getAll();

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public void delete(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public LeaveResponse update(Long id, LeaveRequest request);
}
