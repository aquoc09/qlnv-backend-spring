package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.RewardDisciplineRequest;
import com.kenji.qlnv_backend.dto.response.RewardDisciplineResponse;
import com.kenji.qlnv_backend.entity.RewardDiscipline;

import java.time.LocalDate;
import java.util.List;

public interface RewardDisciplineService {
    public RewardDisciplineResponse create(RewardDisciplineRequest request);

    public RewardDisciplineResponse get(Long id);

    public List<RewardDisciplineResponse> getAll();

    public List<RewardDisciplineResponse> getAllByDate(LocalDate date);

    public void delete(Long id);

    public RewardDisciplineResponse update(Long id, RewardDisciplineRequest request);

    List<RewardDiscipline> getAllRewardByEmployeeAndYearAndMonth(Long empId, int year, int month);

    List<RewardDiscipline> getAllDisciplineByEmployeeAndYearAndMonth(Long empId, int year, int month);
}
