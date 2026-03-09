package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.RoleRequest;
import com.kenji.qlnv_backend.dto.response.RoleResponse;
import com.kenji.qlnv_backend.entity.Role;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.RoleMapper;
import com.kenji.qlnv_backend.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public RoleResponse create(RoleRequest request){
        roleRepository.findById(request.getName()).orElseThrow(() -> new AppException(ErrorCode.ROLE_EXISTED));

        Role user = roleMapper.toRole(request);

        return roleMapper.toRoleResponse(roleRepository.save(user));
    }

    public RoleResponse get(String name){
        Role role = roleRepository.findById(name).orElseThrow(() -> new AppException(ErrorCode.ROLE_EXISTED));
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        List<RoleResponse> roleResponses = new ArrayList<>();
        roleRepository.findAll().forEach(role -> roleResponses.add(roleMapper.toRoleResponse(role)));
        return roleResponses;
    }

    public void delete(String name){
        roleRepository.deleteById(name);
    }

    public RoleResponse update(String name, RoleRequest request){
        Role user = roleRepository.findById(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        roleMapper.updateRole(user, request);

        return roleMapper.toRoleResponse(roleRepository.save(user));

    }

}
