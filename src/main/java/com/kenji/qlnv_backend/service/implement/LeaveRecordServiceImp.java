package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.LeaveRecordRequest;
import com.kenji.qlnv_backend.dto.response.LeaveRecordResponse;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.Leave;
import com.kenji.qlnv_backend.entity.LeaveRecord;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.LeaveRecordMapper;
import com.kenji.qlnv_backend.repository.EmployeeRepository;
import com.kenji.qlnv_backend.repository.LeaveRecordRepository;
import com.kenji.qlnv_backend.repository.LeaveRepository;
import com.kenji.qlnv_backend.service.LeaveRecordService;
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
public class LeaveRecordServiceImp implements LeaveRecordService {
    LeaveRecordRepository leaveRecordRepository;
    EmployeeRepository employeeRepository;
    LeaveRepository leaveRepository;
    LeaveRecordMapper leaveRecordMapper;

    @Override
    public LeaveRecordResponse create(LeaveRecordRequest request) {
        LeaveRecord leaveRecord = leaveRecordMapper.toLeaveRecord(request);

        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
            leaveRecord.setEmployee(employee);
        }

        if (request.getLeaveId() != null) {
            Leave leave = leaveRepository.findById(request.getLeaveId())
                    .orElseThrow(() -> new AppException(ErrorCode.LEAVE_NOT_EXISTED));
            leaveRecord.setLeave(leave);
        }

        return leaveRecordMapper.toLeaveRecordResponse(leaveRecordRepository.save(leaveRecord));
    }

    @Override
    public LeaveRecordResponse get(Long id) {
        LeaveRecord leaveRecord = leaveRecordRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_RECORD_NOT_EXISTED));
        return leaveRecordMapper.toLeaveRecordResponse(leaveRecord);
    }

    @Override
    public List<LeaveRecordResponse> getAll() {
        List<LeaveRecordResponse> responses = new ArrayList<>();
        leaveRecordRepository.findAll()
                .forEach(record -> responses.add(leaveRecordMapper.toLeaveRecordResponse(record)));
        return responses;
    }

    @Override
    public void delete(Long id) {
        leaveRecordRepository.deleteById(id);
    }

    @Override
    public LeaveRecordResponse update(Long id, LeaveRecordRequest request) {
        LeaveRecord leaveRecord = leaveRecordRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_RECORD_NOT_EXISTED));

        if (request.getEmployeeId() != null) {
            if (leaveRecord.getEmployee() == null ||
                    !Objects.equals(leaveRecord.getEmployee().getId(), request.getEmployeeId())) {
                Employee employee = employeeRepository.findById(request.getEmployeeId())
                        .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
                leaveRecord.setEmployee(employee);
            }
        }

        if (request.getLeaveId() != null) {
            if (leaveRecord.getLeave() == null ||
                    !Objects.equals(leaveRecord.getLeave().getId(), request.getLeaveId())) {
                Leave leave = leaveRepository.findById(request.getLeaveId())
                        .orElseThrow(() -> new AppException(ErrorCode.LEAVE_NOT_EXISTED));
                leaveRecord.setLeave(leave);
            }
        }

        leaveRecordMapper.updateLeaveRecord(leaveRecord, request);

        return leaveRecordMapper.toLeaveRecordResponse(leaveRecordRepository.save(leaveRecord));
    }
}
