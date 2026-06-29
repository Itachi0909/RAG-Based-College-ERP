package com.rcet.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeacherSubjectRequest {

    @NotNull(message = "Teacher is required")
    private Long teacherId;

    @NotNull(message = "Subject is required")
    private Long subjectId;
}