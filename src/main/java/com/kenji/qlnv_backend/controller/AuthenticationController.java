package com.kenji.qlnv_backend.controller;

import com.kenji.qlnv_backend.dto.request.AuthenticationRequest;
import com.kenji.qlnv_backend.dto.request.IntrospectRequest;
import com.kenji.qlnv_backend.dto.response.ApiResponse;
import com.kenji.qlnv_backend.dto.response.AuthenticationResponse;
import com.kenji.qlnv_backend.dto.response.IntrospectResponse;
import com.kenji.qlnv_backend.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request){
        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(request))
                .build();
    }

}
