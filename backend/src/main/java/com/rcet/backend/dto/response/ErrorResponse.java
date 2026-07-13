package com.rcet.backend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ErrorResponse {

    private boolean success;

    private int status;

    private String message;

    private LocalDateTime timestamp;

    private Map<String, String> errors;
}