package com.rcet.backend.service;

import com.rcet.backend.dto.request.LoginRequest;
import com.rcet.backend.dto.request.RegisterAdminRequest;
import com.rcet.backend.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse registerAdmin(RegisterAdminRequest request);
}