package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.AttendanceRequest;
import com.kenji.qlnv_backend.dto.response.AttendanceResponse;
import com.kenji.qlnv_backend.entity.Attendance;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.AttendanceMapper;
import com.kenji.qlnv_backend.repository.AttendanceRepository;
import com.kenji.qlnv_backend.repository.EmployeeRepository;
import com.kenji.qlnv_backend.service.AttendanceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AttendanceServiceImp implements AttendanceService {
    AttendanceRepository attendanceRepository;
    EmployeeRepository employeeRepository;
    AttendanceMapper attendanceMapper;

    @Override
    public AttendanceResponse create(AttendanceRequest request) {
        Attendance attendance = attendanceMapper.toAttendance(request);

        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
            attendance.setEmployee(employee);
        }

        return attendanceMapper.toAttendanceResponse(attendanceRepository.save(attendance));
    }

    @Override
    public AttendanceResponse get(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTENDANCE_NOT_EXISTED));
        return attendanceMapper.toAttendanceResponse(attendance);
    }

    @Override
    public List<AttendanceResponse> getAll() {
        List<AttendanceResponse> responses = new ArrayList<>();
        attendanceRepository.findAll()
                .forEach(attendance -> responses.add(attendanceMapper.toAttendanceResponse(attendance)));
        return responses;
    }

    @Override
    public void delete(Long id) {
        attendanceRepository.deleteById(id);
    }

    @Override
    public AttendanceResponse update(Long id, AttendanceRequest request) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTENDANCE_NOT_EXISTED));

        if (request.getEmployeeId() != null) {
            if (attendance.getEmployee() == null ||
                    !Objects.equals(attendance.getEmployee().getId(), request.getEmployeeId())) {
                Employee employee = employeeRepository.findById(request.getEmployeeId())
                        .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
                attendance.setEmployee(employee);
            }
        }

        attendanceMapper.updateAttendance(attendance, request);

        return attendanceMapper.toAttendanceResponse(attendanceRepository.save(attendance));
    }
}
