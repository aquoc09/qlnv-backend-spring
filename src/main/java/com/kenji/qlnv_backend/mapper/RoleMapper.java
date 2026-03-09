package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.RoleRequest;
import com.kenji.qlnv_backend.dto.response.RoleResponse;
import com.kenji.qlnv_backend.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);

    @Mapping(target = "permissions", source = "permissions")
    RoleResponse toRoleResponse(Role role);

    void updateRole(@MappingTarget Role role, RoleRequest request);

}
