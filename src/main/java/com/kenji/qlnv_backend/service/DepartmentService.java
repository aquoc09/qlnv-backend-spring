package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.DepartmentRequest;
import com.kenji.qlnv_backend.dto.response.DepartmentResponse;
import com.kenji.qlnv_backend.entity.Department;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface DepartmentService {
    public DepartmentResponse create(DepartmentRequest request);

    public DepartmentResponse get(Long depId);
    public List<DepartmentResponse> getAll();

    public void delete(Long empId);

    public DepartmentResponse update(Long depId, DepartmentRequest request);
}
