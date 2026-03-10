package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.EmployeeRequest;
import com.kenji.qlnv_backend.dto.response.EmployeeResponse;
import com.kenji.qlnv_backend.entity.Department;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.Role;
import com.kenji.qlnv_backend.entity.User;
import com.kenji.qlnv_backend.enums.RoleEnum;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

public interface EmployeeService {
    public EmployeeResponse create(EmployeeRequest request);

    public EmployeeResponse get(Long empId);

    public EmployeeResponse getMyInfo();

    public List<EmployeeResponse> getAll();

    public void delete(Long empId);

    public EmployeeResponse update(Long empId, EmployeeRequest request);
}
