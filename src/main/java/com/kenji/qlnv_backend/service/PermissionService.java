package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.PermissionRequest;
import com.kenji.qlnv_backend.dto.response.PermissionResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface PermissionService {
    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse create(PermissionRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse get(String name);

    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAll();

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String name);

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse update(String name, PermissionRequest request);
}
