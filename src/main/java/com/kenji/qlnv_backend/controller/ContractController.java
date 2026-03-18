package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.ContractRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.ContractResponse;
import com.kenji.qlnv_backend.service.ContractService;
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
@RequestMapping("/contracts")
public class ContractController {
    ContractService contractService;

    @PostMapping
    ApiResponse<ContractResponse> create(@Valid @RequestBody ContractRequest request) {
        return ApiResponse.<ContractResponse>builder()
                .result(contractService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<ContractResponse>> getAll() {
        List<ApiResponse<ContractResponse>> apiResponses = new ArrayList<>();
        contractService.getAll().forEach(contractResponse -> apiResponses.add(
                ApiResponse.<ContractResponse>builder()
                        .result(contractResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{id}")
    ApiResponse<ContractResponse> get(@PathVariable Long id) {
        return ApiResponse.<ContractResponse>builder()
                .result(contractService.get(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> delete(@PathVariable Long id) {
        contractService.delete(id);
        return ApiResponse.<String>builder()
                .message("Contract has been deleted")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ContractResponse> update(@PathVariable Long id,
                                         @RequestBody @Valid ContractRequest request) {
        return ApiResponse.<ContractResponse>builder()
                .result(contractService.update(id, request))
                .build();
    }

//    @GetMapping("/almost-expired")
//    List<ApiResponse<ContractResponse>> getAllAlmostExpired() {
//        List<ApiResponse<ContractResponse>> apiResponses = new ArrayList<>();
//        contractService.getAll().forEach(contractResponse -> apiResponses.add(
//                ApiResponse.<ContractResponse>builder()
//                        .result(contractResponse)
//                        .build()));
//        return apiResponses;
//    }
}
