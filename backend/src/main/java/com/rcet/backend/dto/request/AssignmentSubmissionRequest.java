package com.rcet.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AssignmentSubmissionRequest {

    @NotNull(message = "Assignment is required")
    private Long assignmentId;

    @NotNull(message = "Student is required")
    private Long studentId;

    @NotBlank(message = "Submission text is required")
    private String submissionText;

    public AssignmentSubmissionRequest() {
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getSubmissionText() {
        return submissionText;
    }

    public void setSubmissionText(String submissionText) {
        this.submissionText = submissionText;
    }
}