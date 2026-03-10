package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.AttendanceRequest;
import com.kenji.qlnv_backend.dto.response.AttendanceResponse;

import java.util.List;

public interface AttendanceService {
    public AttendanceResponse create(AttendanceRequest request);

    public AttendanceResponse get(Long id);

    public List<AttendanceResponse> getAll();

    public void delete(Long id);

    public AttendanceResponse update(Long id, AttendanceRequest request);
}
