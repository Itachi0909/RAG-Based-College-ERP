package com.rcet.backend.dto.response;

import com.rcet.backend.entity.DayOfWeek;
import com.rcet.backend.entity.LectureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimetableResponse {

    private Long id;

    // Teacher
    private Long teacherId;
    private String teacherName;

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

    // Timetable
    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private String roomNumber;

    private LectureType lectureType;

    private Boolean active;
}