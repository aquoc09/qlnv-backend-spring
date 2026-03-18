package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.SalaryRequest;
import com.kenji.qlnv_backend.dto.response.SalaryResponse;
import com.kenji.qlnv_backend.entity.Salary;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SalaryMapper {

    @Mapping(target = "employee", ignore = true)
    Salary toSalary(SalaryRequest request);

    SalaryResponse toSalaryResponse(Salary salary);

    @Mapping(target = "employee", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSalary(@MappingTarget Salary salary, SalaryRequest request);
}
