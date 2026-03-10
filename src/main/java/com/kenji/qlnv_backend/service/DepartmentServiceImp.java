package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.DepartmentRequest;
import com.kenji.qlnv_backend.dto.response.DepartmentResponse;
import com.kenji.qlnv_backend.entity.Department;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.DepartmentMapper;
import com.kenji.qlnv_backend.repository.DepartmentRepository;
import com.kenji.qlnv_backend.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DepartmentServiceImp implements DepartmentService{
    DepartmentRepository departmentRepository;
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentMapper departmentMapper;

    public DepartmentResponse create(DepartmentRequest request){
        Department dep = departmentMapper.toDepartment(request);

        // mapping manager
        if (request.getManagerId() != null) {
            Employee manager = employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
            dep.setManager(manager);
        }

        // mapping employee
        if (request.getEmployees() != null && !request.getEmployees().isEmpty()) {
            List<Employee> employees = employeeRepository.findAllById(request.getEmployees());
            dep.setEmployees(employees);
        }

        return departmentMapper.toDepartmentResponse(departmentRepository.save(dep));
    }

    public DepartmentResponse get(Long depId){
        Department dep = departmentRepository.findById(depId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return departmentMapper.toDepartmentResponse(dep);
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<DepartmentResponse> getAll(){
        List<DepartmentResponse> departmentResponses = new ArrayList<>();
        departmentRepository.findAll()
                .forEach(emp -> departmentResponses.add(departmentMapper.toDepartmentResponse(emp)));
        return departmentResponses;
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public void delete(Long empId){
        departmentRepository.deleteById(empId);
    }

    public DepartmentResponse update(Long depId, DepartmentRequest request){
        Department dep = departmentRepository.findById(depId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));

        departmentMapper.updateDepartment(dep, request);

        // mapping manager
        if (request.getManagerId() != null) {
            if(!Objects.equals(dep.getManager().getId(), request.getManagerId())) {
                Employee manager = employeeRepository.findById(request.getManagerId())
                        .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
                dep.setManager(manager);
            }
        }

        // mapping employee
        if (request.getEmployees() != null && !request.getEmployees().isEmpty()) {
            List<Employee> employees = employeeRepository.findAllById(request.getEmployees());
            dep.setEmployees(employees);
        }

        return departmentMapper.toDepartmentResponse(departmentRepository.save(dep));

    }

}
