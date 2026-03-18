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
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public DepartmentResponse create(DepartmentRequest request);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public DepartmentResponse get(Long depId);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<DepartmentResponse> getAll();

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public void delete(Long empId);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public DepartmentResponse update(Long depId, DepartmentRequest request);
}
