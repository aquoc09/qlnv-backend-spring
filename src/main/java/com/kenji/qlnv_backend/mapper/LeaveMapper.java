package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.LeaveRequest;
import com.kenji.qlnv_backend.dto.response.LeaveResponse;
import com.kenji.qlnv_backend.entity.Leave;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LeaveMapper {

    Leave toLeave(LeaveRequest request);

    LeaveResponse toLeaveResponse(Leave leave);

    void updateLeave(@MappingTarget Leave leave, LeaveRequest request);
}
