package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.LeaveRecordRequest;
import com.kenji.qlnv_backend.dto.response.LeaveRecordResponse;

import java.util.List;

public interface LeaveRecordService {
    public LeaveRecordResponse create(LeaveRecordRequest request);

    public LeaveRecordResponse get(Long id);

    public List<LeaveRecordResponse> getAll();

    public void delete(Long id);

    public LeaveRecordResponse update(Long id, LeaveRecordRequest request);
}
