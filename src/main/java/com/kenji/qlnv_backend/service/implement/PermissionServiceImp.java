package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.PermissionRequest;
import com.kenji.qlnv_backend.dto.response.PermissionResponse;
import com.kenji.qlnv_backend.entity.Permission;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.PermissionMapper;
import com.kenji.qlnv_backend.repository.PermissionRepository;
import com.kenji.qlnv_backend.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionServiceImp implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toRole(request);
        return permissionMapper.toRoleResponse(permissionRepository.save(permission));
    }

    @Override
    public PermissionResponse get(String name) {
        Permission permission = permissionRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));
        return permissionMapper.toRoleResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        List<PermissionResponse> responses = new ArrayList<>();
        permissionRepository.findAll()
                .forEach(permission -> responses.add(permissionMapper.toRoleResponse(permission)));
        return responses;
    }

    @Override
    public void delete(String name) {
        permissionRepository.deleteById(name);
    }

    @Override
    public PermissionResponse update(String name, PermissionRequest request) {
        Permission permission = permissionRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        permissionMapper.updatePermission(permission, request);

        return permissionMapper.toRoleResponse(permissionRepository.save(permission));
    }
}
