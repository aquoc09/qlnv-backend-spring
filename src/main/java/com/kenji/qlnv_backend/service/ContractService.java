package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.ContractRequest;
import com.kenji.qlnv_backend.dto.response.ContractResponse;

import java.util.List;

public interface ContractService {
    public ContractResponse create(ContractRequest request);

    public ContractResponse get(Long id);

    public List<ContractResponse> getAll();

    public void delete(Long id);

    public List<ContractResponse> getListAlmostExpiredContract(Long id);

    public ContractResponse update(Long id, ContractRequest request);
}
