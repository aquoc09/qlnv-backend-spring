package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.LeaveRequest;
import com.kenji.qlnv_backend.dto.response.LeaveResponse;
import com.kenji.qlnv_backend.entity.Leave;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.LeaveMapper;
import com.kenji.qlnv_backend.repository.LeaveRepository;
import com.kenji.qlnv_backend.service.LeaveService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LeaveServiceImp implements LeaveService {
    LeaveRepository leaveRepository;
    LeaveMapper leaveMapper;

    @Override
    public LeaveResponse create(LeaveRequest request) {
        Leave leave = leaveMapper.toLeave(request);
        return leaveMapper.toLeaveResponse(leaveRepository.save(leave));
    }

    @Override
    public LeaveResponse get(Long id) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_NOT_EXISTED));
        return leaveMapper.toLeaveResponse(leave);
    }

    @Override
    public List<LeaveResponse> getAll() {
        List<LeaveResponse> responses = new ArrayList<>();
        leaveRepository.findAll()
                .forEach(leave -> responses.add(leaveMapper.toLeaveResponse(leave)));
        return responses;
    }

    @Override
    public void delete(Long id) {
        leaveRepository.deleteById(id);
    }

    @Override
    public LeaveResponse update(Long id, LeaveRequest request) {
        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_NOT_EXISTED));

        leaveMapper.updateLeave(leave, request);

        return leaveMapper.toLeaveResponse(leaveRepository.save(leave));
    }
}
