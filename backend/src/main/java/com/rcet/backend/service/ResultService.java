package com.rcet.backend.service;

import com.rcet.backend.dto.response.ResultResponse;

public interface ResultService {

    // Student API
    ResultResponse generateResult(Long semesterId);

    // Internal use (Transcript/Admin)
    ResultResponse generateResult(Long studentId, Long semesterId);
}