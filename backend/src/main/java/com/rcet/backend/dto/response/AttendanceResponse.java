package com.rcet.backend.dto.response;

import com.rcet.backend.entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {

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

    // Attendance Details
    private LocalDate attendanceDate;
    private Integer lectureNumber;
    private AttendanceStatus status;
    private String remarks;
}