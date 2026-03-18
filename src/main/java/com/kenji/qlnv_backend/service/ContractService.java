package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.ContractRequest;
import com.kenji.qlnv_backend.dto.response.ContractResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ContractService {
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ContractResponse create(ContractRequest request);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ContractResponse get(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<ContractResponse> getAll();

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public void delete(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<ContractResponse> getListAlmostExpiredContract(Long id);

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ContractResponse update(Long id, ContractRequest request);
}
