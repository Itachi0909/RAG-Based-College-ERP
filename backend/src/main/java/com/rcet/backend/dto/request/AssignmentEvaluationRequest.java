package com.rcet.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class AssignmentEvaluationRequest {

    @NotNull(message = "Obtained marks are required")
    @PositiveOrZero(message = "Marks cannot be negative")
    private Double obtainedMarks;

    private String remarks;

    public AssignmentEvaluationRequest() {
    }

    public Double getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(Double obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}