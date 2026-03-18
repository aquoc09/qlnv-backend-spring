package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.EmployeeRequest;
import com.kenji.qlnv_backend.dto.response.EmployeeResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.*;

public interface EmployeeService {
    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public EmployeeResponse create(EmployeeRequest request);

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public EmployeeResponse get(Long empId);

    @PreAuthorize("isAuthenticated()")
    public EmployeeResponse getCurrentEmployee();

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public List<EmployeeResponse> getAllEmployeesByName(String name);

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public List<EmployeeResponse> getAllEmployeesByDepartment(Long depId);

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public List<EmployeeResponse> getAll();

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    public void delete(Long empId);

    public EmployeeResponse update(Long empId, EmployeeRequest request);
}
