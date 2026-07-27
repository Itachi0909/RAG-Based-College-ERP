package com.rcet.backend.repository;

import com.rcet.backend.entity.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession, Long> {

    Optional<AttendanceSession> findByTeacherSubjectIdAndAttendanceDateAndStartTime(
            Long teacherSubjectId,
            LocalDate attendanceDate,
            LocalTime startTime
    );

    List<AttendanceSession> findByTeacherSubjectIdOrderByAttendanceDateDescStartTimeDesc(
            Long teacherSubjectId
    );

    List<AttendanceSession> findByTeacherSubjectId(
            Long teacherSubjectId
    );

    List<AttendanceSession> findByAttendanceDate(
            LocalDate attendanceDate
    );

    List<AttendanceSession> findByTeacherSubjectTeacherId(
            Long teacherId
    );
}