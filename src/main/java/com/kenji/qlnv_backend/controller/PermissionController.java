package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.PermissionRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.PermissionResponse;
import com.kenji.qlnv_backend.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/permissions")
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@Valid @RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<PermissionResponse>> getAll() {
        List<ApiResponse<PermissionResponse>> apiResponses = new ArrayList<>();
        permissionService.getAll().forEach(permissionResponse -> apiResponses.add(
                ApiResponse.<PermissionResponse>builder()
                        .result(permissionResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{name}")
    ApiResponse<PermissionResponse> get(@PathVariable String name) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.get(name))
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<String> delete(@PathVariable String name) {
        permissionService.delete(name);
        return ApiResponse.<String>builder()
                .message("Permission has been deleted")
                .build();
    }

    @PutMapping("/{name}")
    ApiResponse<PermissionResponse> update(@PathVariable String name,
                                           @RequestBody @Valid PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.update(name, request))
                .build();
    }
}
