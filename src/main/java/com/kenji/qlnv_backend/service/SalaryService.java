package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.SalaryRequest;
import com.kenji.qlnv_backend.dto.response.SalaryResponse;

import java.util.List;

public interface SalaryService {
    public SalaryResponse create(SalaryRequest request);

    public SalaryResponse get(Long id);

    public List<SalaryResponse> getAll();

    public void delete(Long id);

    public SalaryResponse update(Long id, SalaryRequest request);

    public SalaryResponse calculateSalaryByEmployee(Long empId);

    public List<SalaryResponse> calculateSalaryByMonth(int month, int year);

    public int calculateWorkingDays(int year, int month);
}
