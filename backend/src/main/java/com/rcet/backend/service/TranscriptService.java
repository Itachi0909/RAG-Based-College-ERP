package com.rcet.backend.service;

import com.rcet.backend.dto.response.TranscriptResponse;

public interface TranscriptService {

    TranscriptResponse generateTranscript(
            Long studentId
    );

}