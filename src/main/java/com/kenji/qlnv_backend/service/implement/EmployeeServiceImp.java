package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.EmployeeRequest;
import com.kenji.qlnv_backend.dto.response.EmployeeResponse;
import com.kenji.qlnv_backend.entity.*;
import com.kenji.qlnv_backend.enums.RoleEnum;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.EmployeeMapper;
import com.kenji.qlnv_backend.repository.*;
import com.kenji.qlnv_backend.service.EmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmployeeServiceImp implements EmployeeService {
    EmployeeRepository employeeRepository;
    UserRepository userRepository;
    LeaveBalanceRepository leaveBalanceRepository;
    DepartmentRepository departmentRepository;
    RoleRepository roleRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    @NonFinal
    @Value("${security.bcrypt-strength}")
    int BCRYPT_STRENGTH;

    // Map thông tin của user
    private User getOrCreateUser(EmployeeRequest request, Employee employee) {
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            return userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        }

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findById(RoleEnum.EMPLOYEE.name())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCRYPT_STRENGTH);

        return User.builder()
                .username(employee.getEmail())
                .password(passwordEncoder.encode(employee.getEmail()))
                .roles(roles)
                .build();
    }

    // Tạo leaveBalance cho user hợp lệ
    private void createLeaveBalance(Employee employee) {
        LeaveBalance leaveBalance = LeaveBalance.builder()
                .employee(employee)
                .usedDays(0)
                .year(Calendar.getInstance().get(Calendar.YEAR))
                .build();

        leaveBalanceRepository.save(leaveBalance);
    }

    // Tạo employee mới từ request
    public EmployeeResponse create(EmployeeRequest request) {
        Employee employee = employeeMapper.toEmployee(request);

        User user = getOrCreateUser(request, employee);
        employee.setUser(userRepository.save(user));

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));
            employee.setDepartment(department);
        }

        Employee savedEmployee = employeeRepository.save(employee);

        createLeaveBalance(savedEmployee);

        return employeeMapper.toEmployeeResponse(savedEmployee);
    }

    public EmployeeResponse get(Long empId) {
        Employee emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return employeeMapper.toEmployeeResponse(emp);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public EmployeeResponse getCurrentEmployee() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Employee emp = employeeRepository.findByUser_Username(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return employeeMapper.toEmployeeResponse(emp);
    }

    @Override
    public List<EmployeeResponse> getAllEmployeesByName(String name) {
        List<Employee> employees = employeeRepository.findByEmpName(name);
        return employees.stream()
                .map(employeeMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponse> getAllEmployeesByDepartment(Long depId) {
        Department dep = departmentRepository.findById(depId)
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));
        List<Employee> employees = employeeRepository.findByDepartment(dep);
        return employees.stream()
                .map(employeeMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<EmployeeResponse> getAll() {
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        employeeRepository.findAll()
                .forEach(emp -> employeeResponses.add(employeeMapper.toEmployeeResponse(emp)));
        return employeeResponses;
    }

    @Transactional
    public void delete(Long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
        employeeRepository.delete(employee);
    }

    public EmployeeResponse update(Long empId, EmployeeRequest request) {
        Employee emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            if (!Objects.equals(emp.getUser().getUsername(), request.getUsername())) {
                User user = userRepository.findByUsername(request.getUsername())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
                emp.setUser(user);
            }
        }
        if (request.getDepartmentId() != null) {
            if (!Objects.equals(emp.getDepartment().getId(), request.getDepartmentId())) {
                Department dep = departmentRepository.findById(request.getDepartmentId())
                        .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXISTED));
                emp.setDepartment(dep);
            }
        }

        employeeMapper.updateEmployee(emp, request);

        return employeeMapper.toEmployeeResponse(employeeRepository.save(emp));

    }

}
