package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.PermissionRequest;
import com.kenji.qlnv_backend.dto.response.PermissionResponse;
import com.kenji.qlnv_backend.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toRole(PermissionRequest request);

    PermissionResponse toRoleResponse(Permission permission);

    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);

}
