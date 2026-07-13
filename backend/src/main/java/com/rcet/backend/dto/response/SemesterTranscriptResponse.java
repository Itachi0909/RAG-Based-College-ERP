package com.rcet.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SemesterTranscriptResponse {

    private Long semesterId;

    private Integer semesterNumber;

    private Double sgpa;

    private String grade;

    private Boolean passed;
}