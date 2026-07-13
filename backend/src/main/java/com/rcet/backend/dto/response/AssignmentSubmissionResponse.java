package com.rcet.backend.dto.response;

import com.rcet.backend.entity.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentSubmissionResponse {

    private Long id;

    // Student
    private Long studentId;
    private String enrollmentNumber;
    private String studentName;

    // Assignment
    private Long assignmentId;
    private String assignmentTitle;

    // Subject
    private Long subjectId;
    private String subjectCode;
    private String subjectName;

    // Submission
    private String submissionText;
    private String attachmentUrl;
    private LocalDateTime submittedAt;
    private SubmissionStatus status;

    // Evaluation
    private Double maximumMarks;
    private Double obtainedMarks;
    private String remarks;
}