package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.ContractRequest;
import com.kenji.qlnv_backend.dto.response.ContractResponse;
import com.kenji.qlnv_backend.entity.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "employee", ignore = true)
    Contract toContract(ContractRequest request);

    ContractResponse toContractResponse(Contract contract);

    @Mapping(target = "employee", ignore = true)
    void updateContract(@MappingTarget Contract contract, ContractRequest request);
}
