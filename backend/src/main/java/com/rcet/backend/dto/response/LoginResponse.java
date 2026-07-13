package com.rcet.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

    private String token;
    private Long userId;

    @Builder.Default
    private String type = "Bearer";

    private String email;

    private String role;
}