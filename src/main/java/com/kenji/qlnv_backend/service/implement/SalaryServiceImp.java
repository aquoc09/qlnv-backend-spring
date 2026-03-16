package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.SalaryRequest;
import com.kenji.qlnv_backend.dto.response.SalaryResponse;
import com.kenji.qlnv_backend.entity.*;
import com.kenji.qlnv_backend.enums.ContractType;
import com.kenji.qlnv_backend.enums.SalaryStatus;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.SalaryMapper;
import com.kenji.qlnv_backend.repository.*;
import com.kenji.qlnv_backend.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SalaryServiceImp implements SalaryService {
    SalaryRepository salaryRepository;
    EmployeeRepository employeeRepository;
    AttendanceRepository attendanceRepository;
    ContractRepository contractRepository;
    LeaveRecordService leaveRecordService;
    LeaveBalanceService leaveBalanceService;
    AttendanceService attendanceService;
    RewardDisciplineService rewardDisciplineService;
    SalaryMapper salaryMapper;

    @Override
    public SalaryResponse create(SalaryRequest request) {
        Salary salary = salaryMapper.toSalary(request);

        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
            salary.setEmployee(employee);
        }

        return salaryMapper.toSalaryResponse(salaryRepository.save(salary));
    }

    @Override
    public SalaryResponse get(Long id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SALARY_NOT_EXISTED));
        return salaryMapper.toSalaryResponse(salary);
    }

    @Override
    public List<SalaryResponse> getAll() {
        List<SalaryResponse> responses = new ArrayList<>();
        salaryRepository.findAll()
                .forEach(salary -> responses.add(salaryMapper.toSalaryResponse(salary)));
        return responses;
    }

    @Override
    public void delete(Long id) {
        salaryRepository.deleteById(id);
    }

    @Override
    public SalaryResponse update(Long id, SalaryRequest request) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SALARY_NOT_EXISTED));

        if (request.getEmployeeId() != null) {
            if (salary.getEmployee() == null ||
                    !Objects.equals(salary.getEmployee().getId(), request.getEmployeeId())) {
                Employee employee = employeeRepository.findById(request.getEmployeeId())
                        .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
                salary.setEmployee(employee);
            }
        }

        salaryMapper.updateSalary(salary, request);

        return salaryMapper.toSalaryResponse(salaryRepository.save(salary));
    }

    @Override
    public SalaryResponse calculateSalaryByEmployee(Long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));

        List<Attendance> attendances = attendanceRepository.findAllByEmployee(employee);



        return null;
    }

    @Override
    public List<SalaryResponse> calculateSalaryByMonth(int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }

        YearMonth targetMonth = YearMonth.of(year, month);
        LocalDate monthStart = targetMonth.atDay(1);
        LocalDate monthEnd = targetMonth.atEndOfMonth();
        int workingDays = calculateWorkingDays(year, month);

        List<Salary> existingSalaries = salaryRepository.findAll();
        List<Salary> createdSalaries = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            try {
                boolean salaryAlreadyExists = existingSalaries.stream()
                        .anyMatch(salary -> salary.getEmployee() != null
                                && Objects.equals(salary.getEmployee().getId(), employee.getId())
                                && Objects.equals(salary.getMonth(), month)
                                && Objects.equals(salary.getYear(), year));

                if (salaryAlreadyExists) {
                    log.info("Skip salary calculation for employee {} in {}/{} because salary already exists",
                            employee.getId(), month, year);
                    continue;
                }

                Contract contract = contractRepository.findByEmployee(employee)
                        .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_EXISTED));

                if (contract.getContractType() != ContractType.FULL_TIME) {
                    log.info("Skip salary calculation for employee {} because contract type is not FULL_TIME",
                            employee.getId());
                    continue;
                }

                if (contract.getStartDate() != null && contract.getStartDate().isAfter(monthEnd)) {
                    log.info("Skip salary calculation for employee {} because contract starts after target month",
                            employee.getId());
                    continue;
                }

                if (contract.getEndDate() != null && contract.getEndDate().isBefore(monthStart)) {
                    log.info("Skip salary calculation for employee {} because contract ended before target month",
                            employee.getId());
                    continue;
                }

                List<Attendance> attendances = attendanceService.findAllByEmployeeAndMonthYear(employee, month, year);
                List<LeaveRecord> leaveRecords = leaveRecordService.getAcceptedLeaveByMonth(employee, month, year);
                LeaveBalance leaveBalance = leaveBalanceService.getLeaveBalanceByEmployeeAndYear(employee.getId(), year);
                List<RewardDiscipline> rewards = rewardDisciplineService
                        .getAllRewardByEmployeeAndYearAndMonth(employee.getId(), year, month);
                List<RewardDiscipline> disciplines = rewardDisciplineService
                        .getAllDisciplineByEmployeeAndYearAndMonth(employee.getId(), year, month);

                int totalWorkedDays = attendances.size();
                int totalLeaveDays = leaveRecords.size();

                if (totalWorkedDays > workingDays) {
                    log.warn("Skip salary calculation for employee {} because worked days {} exceed working days {}",
                            employee.getId(), totalWorkedDays, workingDays);
                    continue;
                }

                if (leaveBalance != null && totalLeaveDays > leaveBalance.getTotalDays()) {
                    log.warn("Skip salary calculation for employee {} because leave days {} exceed remaining balance {}",
                            employee.getId(), totalLeaveDays, leaveBalance.getTotalDays());
                    continue;
                }

                BigDecimal deduction = getDeductionByDisciplines(disciplines);
                BigDecimal bonus = getBonusByRewards(rewards);
                BigDecimal netSalary = contract.getBaseSalary()
                        .subtract(deduction)
                        .add(bonus);

                if (netSalary.compareTo(BigDecimal.ZERO) < 0) {
                    netSalary = BigDecimal.ZERO;
                }

                Salary salary = Salary.builder()
                        .employee(employee)
                        .status(SalaryStatus.PENDING)
                        .month(month)
                        .year(year)
                        .bonus(bonus)
                        .deduction(deduction)
                        .netSalary(netSalary)
                        .baseSalary(contract.getBaseSalary())
                        .build();

                Salary savedSalary = salaryRepository.save(salary);
                createdSalaries.add(savedSalary);
                existingSalaries.add(savedSalary);
            } catch (AppException exception) {
                log.warn("Skip salary calculation for employee {} in {}/{}: {}",
                        employee.getId(), month, year, exception.getMessage());
            } catch (Exception exception) {
                log.error("Unexpected error while calculating salary for employee {} in {}/{}",
                        employee.getId(), month, year, exception);
            }
        }

        return createdSalaries.stream()
                .map(salaryMapper::toSalaryResponse)
                .toList();
    }

    private BigDecimal getDeductionByDisciplines(List<RewardDiscipline> disciplines) {
        BigDecimal deduction = BigDecimal.ZERO;
        for (RewardDiscipline discipline : disciplines) {
            if (discipline.getAmount() != null) {
                deduction = deduction.add(discipline.getAmount());
            }
        }
        return deduction;
    }

    private BigDecimal getBonusByRewards(List<RewardDiscipline> rewards) {
        BigDecimal bonus = BigDecimal.ZERO;
        for (RewardDiscipline reward : rewards) {
            if (reward.getAmount() != null) {
                bonus = bonus.add(reward.getAmount());
            }
        }
        return bonus;
    }

    @Override
    public int calculateWorkingDays(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        int workingDays = 0;

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);
            DayOfWeek dow = date.getDayOfWeek();

            if (dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY) {
                workingDays++;
            }
        }
        return workingDays;
    }
}