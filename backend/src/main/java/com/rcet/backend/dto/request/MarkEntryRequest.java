package com.rcet.backend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MarkEntryRequest {

    @NotNull
    private Long studentId;

    @NotNull
    @Min(0)

    private Integer internalMarks;

    @NotNull
    @Min(0)

    private Integer theoryMarks;

    public MarkEntryRequest() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(Integer internalMarks) {
        this.internalMarks = internalMarks;
    }

    public Integer getTheoryMarks() {
        return theoryMarks;
    }

    public void setTheoryMarks(Integer theoryMarks) {
        this.theoryMarks = theoryMarks;
    }
}