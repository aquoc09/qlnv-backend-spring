package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.RoleRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.RoleResponse;
import com.kenji.qlnv_backend.service.RoleService;
import com.kenji.qlnv_backend.service.RoleServiceImp;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/roles")
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @GetMapping("/{role}")
    ApiResponse<RoleResponse> get(@PathVariable String role) {
        return ApiResponse.<RoleResponse>builder().result(roleService.get(role)).build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<String> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<String>builder().result("Role has been deleted").build();
    }
}
