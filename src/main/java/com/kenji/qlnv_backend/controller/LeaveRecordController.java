package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.LeaveRecordRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.LeaveRecordResponse;
import com.kenji.qlnv_backend.service.LeaveRecordService;
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
@RequestMapping("/leave-records")
public class LeaveRecordController {
    LeaveRecordService leaveRecordService;

    @PostMapping
    ApiResponse<LeaveRecordResponse> create(@Valid @RequestBody LeaveRecordRequest request) {
        return ApiResponse.<LeaveRecordResponse>builder()
                .result(leaveRecordService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<LeaveRecordResponse>> getAll() {
        List<ApiResponse<LeaveRecordResponse>> apiResponses = new ArrayList<>();
        leaveRecordService.getAll().forEach(leaveRecordResponse -> apiResponses.add(
                ApiResponse.<LeaveRecordResponse>builder()
                        .result(leaveRecordResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{id}")
    ApiResponse<LeaveRecordResponse> get(@PathVariable Long id) {
        return ApiResponse.<LeaveRecordResponse>builder()
                .result(leaveRecordService.get(id))
                .build();
    }

    @GetMapping("/employee/{empId}")
    List<ApiResponse<LeaveRecordResponse>> getAllByEmployee(@PathVariable Long empId) {
        List<ApiResponse<LeaveRecordResponse>> apiResponses = new ArrayList<>();
        leaveRecordService.getAllByEmployee(empId).forEach(leaveRecordResponse -> apiResponses.add(
                ApiResponse.<LeaveRecordResponse>builder()
                        .result(leaveRecordResponse)
                        .build()));
        return apiResponses;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> delete(@PathVariable Long id) {
        leaveRecordService.delete(id);
        return ApiResponse.<String>builder()
                .message("Leave record has been deleted")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<LeaveRecordResponse> update(@PathVariable Long id,
                                            @RequestBody @Valid LeaveRecordRequest request) {
        return ApiResponse.<LeaveRecordResponse>builder()
                .result(leaveRecordService.update(id, request))
                .build();
    }
}
