package com.rcet.backend.dto.request;

import com.rcet.backend.entity.AttendanceStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceRequest {

    @NotNull(message = "Student is required")
    private Long studentId;

    @NotNull(message = "Teacher Subject is required")
    private Long teacherSubjectId;

    @NotNull(message = "Attendance date is required")
    private LocalDate attendanceDate;

    @NotNull(message = "Lecture number is required")
    @Min(value = 1, message = "Lecture number must be at least 1")
    @Max(value = 10, message = "Lecture number cannot exceed 10")
    private Integer lectureNumber;

    @NotNull(message = "Attendance status is required")
    private AttendanceStatus status;

    private String remarks;
}