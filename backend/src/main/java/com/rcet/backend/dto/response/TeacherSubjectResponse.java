package com.rcet.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherSubjectResponse {

    private Long id;

    // Teacher

    private Long teacherId;

    private String employeeId;

    private String teacherName;

    // Semester Subject

    private Long semesterSubjectId;

    // Semester

    private Long semesterId;

    private Integer semesterNumber;

    // Subject

    private Long subjectId;

    private String subjectCode;

    private String subjectName;

    // Branch

    private Long branchId;

    private String branchName;

    // Department

    private Long departmentId;

    private String departmentName;
}