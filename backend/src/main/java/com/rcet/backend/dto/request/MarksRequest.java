package com.rcet.backend.dto.request;

import com.rcet.backend.entity.ExamType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MarksRequest {

    @NotNull(message = "Student is required")
    private Long studentId;

    @NotNull(message = "Teacher Subject is required")
    private Long teacherSubjectId;

    @NotNull(message = "Exam type is required")
    private ExamType examType;

    @NotNull(message = "Maximum marks is required")
    @DecimalMin(value = "1.0", message = "Maximum marks must be greater than 0")
    private Double maximumMarks;

    @NotNull(message = "Obtained marks is required")
    @DecimalMin(value = "0.0", message = "Obtained marks cannot be negative")
    private Double obtainedMarks;

    @NotNull(message = "Weightage is required")
    @DecimalMin(value = "0.0", message = "Weightage cannot be negative")
    private Double weightage;

    private String remarks;
}