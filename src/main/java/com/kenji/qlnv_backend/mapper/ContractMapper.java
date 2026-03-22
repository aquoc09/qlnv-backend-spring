package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.ContractRequest;
import com.kenji.qlnv_backend.dto.response.ContractResponse;
import com.kenji.qlnv_backend.entity.Contract;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "employee", ignore = true)
    Contract toContract(ContractRequest request);

    @Mapping(target = "employeeId", source = "employee.id")
    ContractResponse toContractResponse(Contract contract);

    @Mapping(target = "employee", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContract(@MappingTarget Contract contract, ContractRequest request);
}
