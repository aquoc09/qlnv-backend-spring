package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.LeaveBalanceRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.LeaveBalanceResponse;
import com.kenji.qlnv_backend.service.LeaveBalanceService;
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
@RequestMapping("/leave-balances")
public class LeaveBalanceController {
    LeaveBalanceService leaveBalanceService;

    @PostMapping
    ApiResponse<LeaveBalanceResponse> create(@Valid @RequestBody LeaveBalanceRequest request) {
        return ApiResponse.<LeaveBalanceResponse>builder()
                .result(leaveBalanceService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<LeaveBalanceResponse>> getAll() {
        List<ApiResponse<LeaveBalanceResponse>> apiResponses = new ArrayList<>();
        leaveBalanceService.getAll().forEach(response -> apiResponses.add(
                ApiResponse.<LeaveBalanceResponse>builder()
                        .result(response)
                        .build()
        ));
        return apiResponses;
    }

    @GetMapping("/{id}")
    ApiResponse<LeaveBalanceResponse> get(@PathVariable Long id) {
        return ApiResponse.<LeaveBalanceResponse>builder()
                .result(leaveBalanceService.get(id))
                .build();
    }

    @GetMapping("/employee/{empId}")
    ApiResponse<LeaveBalanceResponse> getByEmployee(@PathVariable Long empId) {
        return ApiResponse.<LeaveBalanceResponse>builder()
                .result(leaveBalanceService.getByEmployee(empId))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<LeaveBalanceResponse> update(@PathVariable Long id,
                                             @Valid @RequestBody LeaveBalanceRequest request) {
        return ApiResponse.<LeaveBalanceResponse>builder()
                .result(leaveBalanceService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> delete(@PathVariable Long id) {
        leaveBalanceService.delete(id);
        return ApiResponse.<String>builder()
                .message("Leave balance has been deleted")
                .build();
    }
}