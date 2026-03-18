package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.SalaryRequest;
import com.kenji.qlnv_backend.dto.response.SalaryResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SalaryService {
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public SalaryResponse create(SalaryRequest request);

    @PreAuthorize("isAuthenticated()")
    public SalaryResponse get(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<SalaryResponse> getAll();

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public void delete(Long id);

    List<SalaryResponse> findAllByEmployee(Long empId);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public SalaryResponse update(Long id, SalaryRequest request);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public SalaryResponse calculateSalaryByEmployee(Long empId);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<SalaryResponse> calculateSalaryByMonth(int month, int year);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public int calculateWorkingDays(int year, int month);
}
