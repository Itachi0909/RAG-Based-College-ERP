package com.rcet.backend.dto.response;

import com.rcet.backend.entity.ExamType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarksResponse {

    private Long id;

    // Student Details
    private Long studentId;
    private String enrollmentNumber;
    private String studentName;

    // Teacher Details
    private Long teacherId;
    private String teacherName;

    // Subject Details
    private Long subjectId;
    private String subjectCode;
    private String subjectName;

    // Marks Details
    private ExamType examType;
    private Double maximumMarks;
    private Double obtainedMarks;
    private Double weightage;

    // Calculated
    private Double percentage;

    private String remarks;
}