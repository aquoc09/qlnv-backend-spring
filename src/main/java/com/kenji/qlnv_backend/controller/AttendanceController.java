package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.AttendanceRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.AttendanceResponse;
import com.kenji.qlnv_backend.service.AttendanceService;
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
@RequestMapping("/attendances")
public class AttendanceController {
    AttendanceService attendanceService;

    @PostMapping
    ApiResponse<AttendanceResponse> create(@Valid @RequestBody AttendanceRequest request) {
        return ApiResponse.<AttendanceResponse>builder()
                .result(attendanceService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<AttendanceResponse>> getAll() {
        List<ApiResponse<AttendanceResponse>> apiResponses = new ArrayList<>();
        attendanceService.getAll().forEach(attendanceResponse -> apiResponses.add(
                ApiResponse.<AttendanceResponse>builder()
                        .result(attendanceResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{id}")
    ApiResponse<AttendanceResponse> get(@PathVariable Long id) {
        return ApiResponse.<AttendanceResponse>builder()
                .result(attendanceService.get(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> delete(@PathVariable Long id) {
        attendanceService.delete(id);
        return ApiResponse.<String>builder()
                .message("Attendance has been deleted")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<AttendanceResponse> update(@PathVariable Long id,
                                           @RequestBody @Valid AttendanceRequest request) {
        return ApiResponse.<AttendanceResponse>builder()
                .result(attendanceService.update(id, request))
                .build();
    }
}