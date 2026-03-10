package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.SalaryRequest;
import com.kenji.qlnv_backend.dto.response.SalaryResponse;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.Salary;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.SalaryMapper;
import com.kenji.qlnv_backend.repository.EmployeeRepository;
import com.kenji.qlnv_backend.repository.SalaryRepository;
import com.kenji.qlnv_backend.service.SalaryService;
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
public class SalaryServiceImp implements SalaryService {
    SalaryRepository salaryRepository;
    EmployeeRepository employeeRepository;
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
}
