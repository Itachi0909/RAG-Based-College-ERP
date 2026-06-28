package com.rcet.backend.service;

import com.rcet.backend.dto.request.LoginRequest;
import com.rcet.backend.dto.request.RegisterRequest;
import com.rcet.backend.dto.response.LoginResponse;

public interface AuthService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}