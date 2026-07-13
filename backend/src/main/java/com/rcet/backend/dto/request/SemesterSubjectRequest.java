package com.rcet.backend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SemesterSubjectRequest {

    @NotNull(message = "Semester ID is required")
    private Long semesterId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "Internal marks are required")
    @Min(value = 0, message = "Internal marks cannot be negative")
    @Max(value = 100, message = "Internal marks cannot exceed 100")
    private Integer internalMarks;

    @NotNull(message = "External marks are required")
    @Min(value = 0, message = "External marks cannot be negative")
    @Max(value = 100, message = "External marks cannot exceed 100")
    private Integer externalMarks;

    @NotNull(message = "Passing marks are required")
    @Min(value = 0, message = "Passing marks cannot be negative")
    @Max(value = 100, message = "Passing marks cannot exceed 100")
    private Integer passingMarks;

    public SemesterSubjectRequest() {
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(Integer internalMarks) {
        this.internalMarks = internalMarks;
    }

    public Integer getExternalMarks() {
        return externalMarks;
    }

    public void setExternalMarks(Integer externalMarks) {
        this.externalMarks = externalMarks;
    }

    public Integer getPassingMarks() {
        return passingMarks;
    }

    public void setPassingMarks(Integer passingMarks) {
        this.passingMarks = passingMarks;
    }
}