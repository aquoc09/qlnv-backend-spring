package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.LeaveRequest;
import com.kenji.qlnv_backend.dto.response.LeaveResponse;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.LeaveRecord;

import java.util.List;

public interface LeaveService {
    public LeaveResponse create(LeaveRequest request);

    public LeaveResponse get(Long id);

    public List<LeaveResponse> getAll();

    public void delete(Long id);

    public LeaveResponse update(Long id, LeaveRequest request);
}
