package com.kenji.qlnv_backend.util;

import com.kenji.qlnv_backend.entity.User;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    static UserRepository userRepository;

    @PreAuthorize("isAuthenticated()")
    public static User getCurrentUser(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        return userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
