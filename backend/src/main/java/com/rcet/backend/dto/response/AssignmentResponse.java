package com.rcet.backend.dto.response;

import com.rcet.backend.entity.AssignmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponse {

    private Long id;

    // Teacher
    private Long teacherId;
    private String teacherName;

    // Teacher Subject
    private Long teacherSubjectId;

    // Semester
    private Long semesterId;
    private Integer semesterNumber;

    // Branch
    private Long branchId;
    private String branchName;

    // Subject
    private Long subjectId;
    private String subjectCode;
    private String subjectName;

    // Assignment
    private String title;
    private String description;
    private AssignmentType assignmentType;
    private LocalDate dueDate;
    private Double maximumMarks;
    private Boolean active;
}