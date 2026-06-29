package com.rcet.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherSubjectResponse {

    private Long id;

    // Teacher Details
    private Long teacherId;
    private String employeeId;
    private String teacherName;

    // Subject Details
    private Long subjectId;
    private String subjectCode;
    private String subjectName;
}