package com.rcet.backend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResultResponse {

    // Student Details
    private Long studentId;
    private String enrollmentNumber;
    private String studentName;

    // Academic Details
    private Long semesterId;
    private Integer semesterNumber;

    // Subject Results
    private List<SubjectResultResponse> subjects;

    // Summary
    private Double totalMaximumMarks;
    private Double totalObtainedMarks;
    private Double percentage;

    // Final Result
    private Double sgpa;
    private String grade;
    private boolean passed;
}