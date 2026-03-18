package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.EmployeeRequest;
import com.kenji.qlnv_backend.dto.response.EmployeeResponse;
import com.kenji.qlnv_backend.entity.Employee;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "department", ignore = true)
    Employee toEmployee(EmployeeRequest request);

    EmployeeResponse toEmployeeResponse(Employee emp);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "department", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployee(@MappingTarget Employee emp, EmployeeRequest request);
}
