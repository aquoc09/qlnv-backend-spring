package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.RoleRequest;
import com.kenji.qlnv_backend.dto.response.RoleResponse;
import com.kenji.qlnv_backend.entity.Role;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;

import java.util.ArrayList;
import java.util.List;

public interface RoleService {
    public RoleResponse create(RoleRequest request);

    public RoleResponse get(String name);

    public List<RoleResponse> getAll();

    public void delete(String name);

    public RoleResponse update(String name, RoleRequest request);
}
