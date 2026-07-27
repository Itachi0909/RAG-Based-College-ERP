package com.rcet.backend.service;

import com.rcet.backend.dto.request.InitializeResultRequest;
import com.rcet.backend.dto.response.InitializeResultResponse;

public interface AdminResultService {

    InitializeResultResponse initializeSemesterResults(
            InitializeResultRequest request);

    void publishResults();

}