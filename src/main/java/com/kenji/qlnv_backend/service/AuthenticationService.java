package com.kenji.qlnv_backend.service;

import com.kenji.qlnv_backend.dto.request.AuthenticationRequest;
import com.kenji.qlnv_backend.dto.request.TokenRequest;
import com.kenji.qlnv_backend.dto.response.AuthenticationResponse;
import com.kenji.qlnv_backend.dto.response.IntrospectResponse;
import com.kenji.qlnv_backend.entity.User;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;

public interface AuthenticationService {
    public IntrospectResponse introspect(TokenRequest request) throws JOSEException, ParseException;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ParseException, JOSEException;

    public boolean logout(TokenRequest request) throws ParseException, JOSEException;

    public String generateToken(User user);

    public AuthenticationResponse refreshToken(TokenRequest request) throws ParseException, JOSEException;

    public User getCurrentUser();
}
