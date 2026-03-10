package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.LeaveRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.LeaveResponse;
import com.kenji.qlnv_backend.service.LeaveService;
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
@RequestMapping("/leaves")
public class LeaveController {
    LeaveService leaveService;

    @PostMapping
    ApiResponse<LeaveResponse> create(@Valid @RequestBody LeaveRequest request) {
        return ApiResponse.<LeaveResponse>builder()
                .result(leaveService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<LeaveResponse>> getAll() {
        List<ApiResponse<LeaveResponse>> apiResponses = new ArrayList<>();
        leaveService.getAll().forEach(leaveResponse -> apiResponses.add(
                ApiResponse.<LeaveResponse>builder()
                        .result(leaveResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{id}")
    ApiResponse<LeaveResponse> get(@PathVariable Long id) {
        return ApiResponse.<LeaveResponse>builder()
                .result(leaveService.get(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> delete(@PathVariable Long id) {
        leaveService.delete(id);
        return ApiResponse.<String>builder()
                .message("Leave has been deleted")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<LeaveResponse> update(@PathVariable Long id,
                                      @RequestBody @Valid LeaveRequest request) {
        return ApiResponse.<LeaveResponse>builder()
                .result(leaveService.update(id, request))
                .build();
    }
}
