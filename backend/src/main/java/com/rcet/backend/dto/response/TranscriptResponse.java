package com.rcet.backend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TranscriptResponse {

    // Student

    private Long studentId;

    private String enrollmentNumber;

    private String studentName;

    private String branchName;

    // Semester History

    private List<SemesterTranscriptResponse> semesters;

    // Overall

    private Double cgpa;

    private Integer completedSemesters;

    private Integer backlogs;

    private Boolean passed;
}