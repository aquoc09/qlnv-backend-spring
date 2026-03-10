package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.UserCreationRequest;
import com.kenji.qlnv_backend.dto.request.UserUpdateRequest;
import com.kenji.qlnv_backend.dto.response.UserResponse;
import com.kenji.qlnv_backend.entity.Role;
import com.kenji.qlnv_backend.entity.User;
import com.kenji.qlnv_backend.enums.RoleEnum;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.UserMapper;
import com.kenji.qlnv_backend.repository.RoleRepository;
import com.kenji.qlnv_backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImp implements UserService{
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    RoleRepository roleRepository;

    public UserResponse create(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(CollectionUtils.isEmpty(user.getRoles())) {
            log.info("Role check is empty");
            Set<Role> roles = new HashSet<>();
            roles.add(
                    roleRepository.findById(RoleEnum.EMPLOYEE.name())
                            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)));
            user.setRoles(roles);
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse get(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<UserResponse> getAll(){
        List<UserResponse> userResponses = new ArrayList<>();
        userRepository.findAll().forEach(user -> userResponses.add(userMapper.toUserResponse(user)));
        return userResponses;
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public UserResponse update(Long userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));

    }

}
