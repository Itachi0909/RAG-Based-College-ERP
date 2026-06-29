package com.rcet.backend.dto.request;

import jakarta.validation.constraints.NotNull;

public class SemesterSubjectRequest {

    @NotNull(message = "Semester ID is required")
    private Long semesterId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

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
}