package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.DepartmentRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.DepartmentResponse;
import com.kenji.qlnv_backend.service.DepartmentService;
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
@RequestMapping("/departments")
public class DepartmentController {
    DepartmentService departmentService;

    @PostMapping
    ApiResponse<DepartmentResponse> create(@Valid @RequestBody DepartmentRequest request){
        return ApiResponse.<DepartmentResponse>builder()
                .result(departmentService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<DepartmentResponse>> getAll(){
        List<ApiResponse<DepartmentResponse>> apiResponses = new ArrayList<>();
        departmentService.getAll().forEach(departmentResponse -> apiResponses.add(
                ApiResponse.<DepartmentResponse>builder()
                        .result(departmentResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{depId}")
    ApiResponse<DepartmentResponse> get(@PathVariable Long depId){
        return ApiResponse.<DepartmentResponse>builder()
                .result(departmentService.get(depId))
                .build();
    }

    @DeleteMapping("/{depId}")
    ApiResponse<String> delete(@PathVariable Long depId){
        departmentService.delete(depId);
        return ApiResponse.<String>builder()
                .message("Department has been deleted")
                .build();
    }

    @PutMapping("/{depId}")
    ApiResponse<DepartmentResponse> update(@PathVariable Long depId, @RequestBody @Valid DepartmentRequest request){
        return ApiResponse.<DepartmentResponse>builder()
                .result(departmentService.update(depId, request))
                .build();
    }
}
