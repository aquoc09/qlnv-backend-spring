package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.LeaveRecordRequest;
import com.kenji.qlnv_backend.dto.response.LeaveRecordResponse;
import com.kenji.qlnv_backend.entity.LeaveRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LeaveRecordMapper {

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "leave", ignore = true)
    LeaveRecord toLeaveRecord(LeaveRecordRequest request);

    LeaveRecordResponse toLeaveRecordResponse(LeaveRecord leaveRecord);

    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "leave", ignore = true)
    void updateLeaveRecord(@MappingTarget LeaveRecord leaveRecord, LeaveRecordRequest request);
}
