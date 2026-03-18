package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.RoleRequest;
import com.kenji.qlnv_backend.dto.response.RoleResponse;
import com.kenji.qlnv_backend.entity.Role;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;

public interface RoleService {
    @PreAuthorize( "hasRole('ADMIN')")
    public RoleResponse create(RoleRequest request);

    @PreAuthorize( "hasRole('ADMIN')")
    public RoleResponse get(String name);

    @PreAuthorize( "hasRole('ADMIN')")
    public List<RoleResponse> getAll();

    @PreAuthorize( "hasRole('ADMIN')")
    public void delete(String name);

    @PreAuthorize( "hasRole('ADMIN')")
    public RoleResponse update(String name, RoleRequest request);
}
