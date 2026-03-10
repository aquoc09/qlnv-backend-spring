package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.UserCreationRequest;
import com.kenji.qlnv_backend.dto.request.UserUpdateRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.UserResponse;
import com.kenji.qlnv_backend.service.UserService;
import com.kenji.qlnv_backend.service.UserServiceImp;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> create(@Valid @RequestBody UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.create(request))
                .build();
    }

    @GetMapping
    List<ApiResponse<UserResponse>> getAll(){
        List<ApiResponse<UserResponse>> apiResponses = new ArrayList<>();
        userService.getAll().forEach(userResponse -> apiResponses.add(
                ApiResponse.<UserResponse>builder()
                        .result(userResponse)
                        .build()));
        return apiResponses;
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> get(@PathVariable Long userId){
        return ApiResponse.<UserResponse>builder()
                .result(userService.get(userId))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<String> delete(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ApiResponse.<String>builder()
                .message("User has been deleted")
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> update(@PathVariable Long userId, @RequestBody @Valid UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.update(userId, request))
                .build();
    }
}
