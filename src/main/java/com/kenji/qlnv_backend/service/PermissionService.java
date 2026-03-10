package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.PermissionRequest;
import com.kenji.qlnv_backend.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    public PermissionResponse create(PermissionRequest request);

    public PermissionResponse get(String name);

    public List<PermissionResponse> getAll();

    public void delete(String name);

    public PermissionResponse update(String name, PermissionRequest request);
}
