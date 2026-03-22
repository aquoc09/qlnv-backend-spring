package com.kenji.qlnv_backend.mapper;

import com.kenji.qlnv_backend.dto.request.AttendanceRequest;
import com.kenji.qlnv_backend.dto.response.AttendanceResponse;
import com.kenji.qlnv_backend.entity.Attendance;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(target = "employee", ignore = true)
    Attendance toAttendance(AttendanceRequest request);

    @Mapping(target = "employeeId", source = "employee.id")
    AttendanceResponse toAttendanceResponse(Attendance attendance);

    @Mapping(target = "employee", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAttendance(@MappingTarget Attendance attendance, AttendanceRequest request);
}
