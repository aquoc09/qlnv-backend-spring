package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.SalaryRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.SalaryResponse;
import com.kenji.qlnv_backend.service.SalaryService;
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
@RequestMapping("/salaries")
public class SalaryController {
    SalaryService salaryService;

    @PostMapping
    ApiResponse<SalaryResponse> create(@Valid @RequestBody SalaryRequest request) {
        return ApiResponse.<SalaryResponse>builder()
                .result(salaryService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<SalaryResponse>> getAll() {
        List<ApiResponse<SalaryResponse>> apiResponses = new ArrayList<>();
        salaryService.getAll().forEach(salaryResponse -> apiResponses.add(
                ApiResponse.<SalaryResponse>builder()
                        .result(salaryResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{id}")
    ApiResponse<SalaryResponse> get(@PathVariable Long id) {
        return ApiResponse.<SalaryResponse>builder()
                .result(salaryService.get(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> delete(@PathVariable Long id) {
        salaryService.delete(id);
        return ApiResponse.<String>builder()
                .message("Salary has been deleted")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<SalaryResponse> update(@PathVariable Long id,
                                       @RequestBody @Valid SalaryRequest request) {
        return ApiResponse.<SalaryResponse>builder()
                .result(salaryService.update(id, request))
                .build();
    }
}
