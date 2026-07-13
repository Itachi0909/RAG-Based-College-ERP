package com.rcet.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubjectResultResponse {

    private Long subjectId;

    private String subjectCode;

    private String subjectName;

    private Integer credits;

    private Double maximumMarks;

    private Double obtainedMarks;

    private Double percentage;

    private String grade;

    private boolean passed;

    private Integer gradePoint;
}