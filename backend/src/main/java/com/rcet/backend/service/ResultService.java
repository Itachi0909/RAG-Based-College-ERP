package com.rcet.backend.service;

import com.rcet.backend.dto.response.ResultResponse;

public interface ResultService {

    ResultResponse generateResult(
            Long studentId,
            Long semesterId
    );
}