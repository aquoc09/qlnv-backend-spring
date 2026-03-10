package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.RewardDisciplineRequest;
import com.kenji.qlnv_backend.dto.response.RewardDisciplineResponse;

import java.util.List;

public interface RewardDisciplineService {
    public RewardDisciplineResponse create(RewardDisciplineRequest request);

    public RewardDisciplineResponse get(Long id);

    public List<RewardDisciplineResponse> getAll();

    public void delete(Long id);

    public RewardDisciplineResponse update(Long id, RewardDisciplineRequest request);
}
