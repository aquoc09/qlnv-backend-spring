package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.LeaveBalanceRequest;
import com.kenji.qlnv_backend.dto.response.LeaveBalanceResponse;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.LeaveBalance;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.LeaveBalanceMapper;
import com.kenji.qlnv_backend.repository.EmployeeRepository;
import com.kenji.qlnv_backend.repository.LeaveBalanceRepository;
import com.kenji.qlnv_backend.service.LeaveBalanceService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LeaveBalanceServiceImp implements LeaveBalanceService {
    LeaveBalanceRepository leaveBalanceRepository;
    LeaveBalanceMapper leaveBalanceMapper;
    EmployeeRepository employeeRepository;

    @Override
    public LeaveBalanceResponse create(LeaveBalanceRequest request) {
        LeaveBalance leaveBalance = leaveBalanceMapper.toLeaveBalance(request);

        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
            leaveBalance.setEmployee(employee);
        }

        return leaveBalanceMapper.toLeaveBalanceResponse(leaveBalanceRepository.save(leaveBalance));
    }

    @Override
    public LeaveBalanceResponse get(Long id) {
        LeaveBalance leaveBalance = leaveBalanceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_BALANCE_NOT_EXISTED));
        return leaveBalanceMapper.toLeaveBalanceResponse(leaveBalance);
    }

    @Override
    public List<LeaveBalanceResponse> getAll() {
        List<LeaveBalanceResponse> responses = new ArrayList<>();
        leaveBalanceRepository.findAll()
                .forEach(item -> responses.add(leaveBalanceMapper.toLeaveBalanceResponse(item)));
        return responses;
    }

    @Override
    public void delete(Long id) {
        leaveBalanceRepository.deleteById(id);
    }

    @Override
    public LeaveBalanceResponse update(Long id, LeaveBalanceRequest request) {
        LeaveBalance leaveBalance = leaveBalanceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_BALANCE_NOT_EXISTED));

        leaveBalanceMapper.updateLeaveBalance(leaveBalance, request);

        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
            leaveBalance.setEmployee(employee);
        }

        return leaveBalanceMapper.toLeaveBalanceResponse(leaveBalanceRepository.save(leaveBalance));
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 1 *") // 00:00 ngày 01/01
    public void resetYearlyLeaveBalance() {
        int year = Year.now().getValue();

        List<Employee> employees = employeeRepository.findAll();

        for (Employee emp : employees) {
            boolean exists = leaveBalanceRepository
                    .existsByEmployeeAndYear(emp, year);

            if (!exists) {
                LeaveBalance balance = LeaveBalance.builder()
                        .employee(emp)
                        .usedDays(0)
                        .year(year)
                        .totalDays(12)
                        .build();

                leaveBalanceRepository.save(balance);
            }
        }
    }

    @Override
    public LeaveBalance getLeaveBalanceByEmployeeAndYear(Long empId, int year) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
        return leaveBalanceRepository.findByEmployeeAndYear(employee, year)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_BALANCE_NOT_EXISTED));
    }

    @Override
    public LeaveBalanceResponse getByEmployee(Long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
        return leaveBalanceMapper
                .toLeaveBalanceResponse(leaveBalanceRepository.findByEmployee(employee)
                .orElseThrow(() -> new AppException(ErrorCode.LEAVE_BALANCE_NOT_EXISTED)));
    }
}