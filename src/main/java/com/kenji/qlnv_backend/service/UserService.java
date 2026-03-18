package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.UserCreationRequest;
import com.kenji.qlnv_backend.dto.request.UserUpdateRequest;
import com.kenji.qlnv_backend.dto.response.UserResponse;
import com.kenji.qlnv_backend.entity.Role;
import com.kenji.qlnv_backend.entity.User;
import com.kenji.qlnv_backend.enums.RoleEnum;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface UserService {
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public UserResponse create(UserCreationRequest request);

    public UserResponse get(Long userId);

    public UserResponse getMyInfo();

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<UserResponse> getAll();

    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public void deleteUser(Long userId);

    public UserResponse update(Long userId, UserUpdateRequest request);
}
