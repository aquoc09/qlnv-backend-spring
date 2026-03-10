package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.EmployeeRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.EmployeeResponse;
import com.kenji.qlnv_backend.service.EmployeeService;
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
@RequestMapping("/employees")
public class EmployeeController {
    EmployeeService employeeService;

    @PostMapping
    ApiResponse<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest request){
        return ApiResponse.<EmployeeResponse>builder()
                .result(employeeService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<EmployeeResponse>> getAll(){
        List<ApiResponse<EmployeeResponse>> apiResponses = new ArrayList<>();
        employeeService.getAll().forEach(employeeResponse -> apiResponses.add(
                ApiResponse.<EmployeeResponse>builder()
                        .result(employeeResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{empId}")
    ApiResponse<EmployeeResponse> get(@PathVariable Long empId){
        return ApiResponse.<EmployeeResponse>builder()
                .result(employeeService.get(empId))
                .build();
    }

    @DeleteMapping("/{empId}")
    ApiResponse<String> delete(@PathVariable Long empId){
        employeeService.delete(empId);
        return ApiResponse.<String>builder()
                .message("Employee has been deleted")
                .build();
    }

    @PutMapping("/{empId}")
    ApiResponse<EmployeeResponse> update(@PathVariable Long empId, @RequestBody @Valid EmployeeRequest request){
        return ApiResponse.<EmployeeResponse>builder()
                .result(employeeService.update(empId, request))
                .build();
    }
}
