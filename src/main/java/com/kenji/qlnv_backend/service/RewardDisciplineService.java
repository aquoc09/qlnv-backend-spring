package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.RewardDisciplineRequest;
import com.kenji.qlnv_backend.dto.response.RewardDisciplineResponse;
import com.kenji.qlnv_backend.entity.RewardDiscipline;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;

public interface RewardDisciplineService {
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public RewardDisciplineResponse create(RewardDisciplineRequest request);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public RewardDisciplineResponse get(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<RewardDisciplineResponse> getAll();

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<RewardDisciplineResponse> getAllByDate(LocalDate date);

    List<RewardDisciplineResponse> getAllByEmployee(Long empId);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public void delete(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public RewardDisciplineResponse update(Long id, RewardDisciplineRequest request);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    List<RewardDiscipline> getAllRewardByEmployeeAndYearAndMonth(Long empId, int year, int month);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    List<RewardDiscipline> getAllDisciplineByEmployeeAndYearAndMonth(Long empId, int year, int month);
}
