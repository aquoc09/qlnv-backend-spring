package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.RewardDisciplineRequest;
import com.kenji.qlnv_backend.dto.response.RewardDisciplineResponse;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.RewardDiscipline;
import com.kenji.qlnv_backend.enums.RewardDisciplineType;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.RewardDisciplineMapper;
import com.kenji.qlnv_backend.repository.EmployeeRepository;
import com.kenji.qlnv_backend.repository.RewardDisciplineRepository;
import com.kenji.qlnv_backend.service.RewardDisciplineService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RewardDisciplineServiceImp implements RewardDisciplineService {
    RewardDisciplineRepository rewardDisciplineRepository;
    EmployeeRepository employeeRepository;
    RewardDisciplineMapper rewardDisciplineMapper;

    @Override
    public RewardDisciplineResponse create(RewardDisciplineRequest request) {
        RewardDiscipline rewardDiscipline = rewardDisciplineMapper.toRewardDiscipline(request);

        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
            rewardDiscipline.setEmployee(employee);
        }

        return rewardDisciplineMapper.toRewardDisciplineResponse(
                rewardDisciplineRepository.save(rewardDiscipline)
        );
    }

    @Override
    public RewardDisciplineResponse get(Long id) {
        RewardDiscipline rewardDiscipline = rewardDisciplineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REWARD_DISCIPLINE_NOT_EXISTED));
        return rewardDisciplineMapper.toRewardDisciplineResponse(rewardDiscipline);
    }

    @Override
    public List<RewardDisciplineResponse> getAll() {
        List<RewardDisciplineResponse> responses = new ArrayList<>();
        rewardDisciplineRepository.findAll()
                .forEach(item -> responses.add(rewardDisciplineMapper.toRewardDisciplineResponse(item)));
        return responses;
    }

    @Override
    public List<RewardDisciplineResponse> getAllByDate(LocalDate date) {
        List<RewardDisciplineResponse> responses = new ArrayList<>();
        rewardDisciplineRepository.findAllByDecisionDate(date)
                .forEach(item -> responses.add(rewardDisciplineMapper.toRewardDisciplineResponse(item)));
        return responses;
    }

    @Override
    public void delete(Long id) {
        rewardDisciplineRepository.deleteById(id);
    }

    @Override
    public RewardDisciplineResponse update(Long id, RewardDisciplineRequest request) {
        RewardDiscipline rewardDiscipline = rewardDisciplineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.REWARD_DISCIPLINE_NOT_EXISTED));

        if (request.getEmployeeId() != null) {
            if (rewardDiscipline.getEmployee() == null ||
                    !Objects.equals(rewardDiscipline.getEmployee().getId(), request.getEmployeeId())) {
                Employee employee = employeeRepository.findById(request.getEmployeeId())
                        .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
                rewardDiscipline.setEmployee(employee);
            }
        }

        rewardDisciplineMapper.updateRewardDiscipline(rewardDiscipline, request);

        return rewardDisciplineMapper.toRewardDisciplineResponse(
                rewardDisciplineRepository.save(rewardDiscipline)
        );
    }

    @Override
    public List<RewardDiscipline> getAllRewardByEmployeeAndYearAndMonth(Long empId, int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));

        return rewardDisciplineRepository.findByEmployeeAndTypeAndMonth(
                employee,
                RewardDisciplineType.REWARD,
                start,
                end
        );
    }

    @Override
    public List<RewardDiscipline> getAllDisciplineByEmployeeAndYearAndMonth(Long empId, int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));

        return rewardDisciplineRepository.findByEmployeeAndTypeAndMonth(
                employee,
                RewardDisciplineType.PENALTY,
                start,
                end
        );
    }
}
