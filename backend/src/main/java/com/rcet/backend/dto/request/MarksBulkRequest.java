package com.rcet.backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class MarksBulkRequest {

    @NotNull
    private Long teacherSubjectId;

    @Valid
    @NotEmpty
    private List<MarkEntryRequest> marks;

    public MarksBulkRequest() {
    }

    public Long getTeacherSubjectId() {
        return teacherSubjectId;
    }

    public void setTeacherSubjectId(Long teacherSubjectId) {
        this.teacherSubjectId = teacherSubjectId;
    }

    public List<MarkEntryRequest> getMarks() {
        return marks;
    }

    public void setMarks(List<MarkEntryRequest> marks) {
        this.marks = marks;
    }
}