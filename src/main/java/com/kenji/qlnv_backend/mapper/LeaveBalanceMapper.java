package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.LeaveBalanceRequest;
import com.kenji.qlnv_backend.dto.response.LeaveBalanceResponse;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.LeaveBalance;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LeaveBalanceMapper {

    @Mapping(target = "employee", ignore = true)
    LeaveBalance toLeaveBalance(LeaveBalanceRequest request);

    @Mapping(target = "employeeId", source = "employee.id")
    LeaveBalanceResponse toLeaveBalanceResponse(LeaveBalance leaveBalance);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "employee", ignore = true)
    void updateLeaveBalance(@MappingTarget LeaveBalance leaveBalance, LeaveBalanceRequest request);
}
