package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.RewardDisciplineRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.RewardDisciplineResponse;
import com.kenji.qlnv_backend.service.RewardDisciplineService;
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
@RequestMapping("/reward-disciplines")
public class RewardDisciplineController {
    RewardDisciplineService rewardDisciplineService;

    @PostMapping
    ApiResponse<RewardDisciplineResponse> create(@Valid @RequestBody RewardDisciplineRequest request) {
        return ApiResponse.<RewardDisciplineResponse>builder()
                .result(rewardDisciplineService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<RewardDisciplineResponse>> getAll() {
        List<ApiResponse<RewardDisciplineResponse>> apiResponses = new ArrayList<>();
        rewardDisciplineService.getAll().forEach(rewardDisciplineResponse -> apiResponses.add(
                ApiResponse.<RewardDisciplineResponse>builder()
                        .result(rewardDisciplineResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{id}")
    ApiResponse<RewardDisciplineResponse> get(@PathVariable Long id) {
        return ApiResponse.<RewardDisciplineResponse>builder()
                .result(rewardDisciplineService.get(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> delete(@PathVariable Long id) {
        rewardDisciplineService.delete(id);
        return ApiResponse.<String>builder()
                .message("Reward discipline has been deleted")
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<RewardDisciplineResponse> update(@PathVariable Long id,
                                                 @RequestBody @Valid RewardDisciplineRequest request) {
        return ApiResponse.<RewardDisciplineResponse>builder()
                .result(rewardDisciplineService.update(id, request))
                .build();
    }
}
