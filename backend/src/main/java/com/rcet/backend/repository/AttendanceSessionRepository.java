package com.rcet.backend.repository;

import com.rcet.backend.entity.AttendanceSession;
import com.rcet.backend.entity.TeacherSubject;
import com.rcet.backend.enums.AttendanceSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceSessionRepository
        extends JpaRepository<AttendanceSession, Long> {

    List<AttendanceSession> findByTeacherSubject(
            TeacherSubject teacherSubject);

    List<AttendanceSession> findByTeacherSubjectAndAttendanceDate(
            TeacherSubject teacherSubject,
            LocalDate attendanceDate);

    List<AttendanceSession> findByAttendanceDate(
            LocalDate attendanceDate);

    List<AttendanceSession> findByStatus(
            AttendanceSessionStatus status);

    List<AttendanceSession> findByTeacherSubjectTeacherId(
            Long teacherId);

    List<AttendanceSession> findByTeacherSubjectTeacherIdAndAttendanceDate(
            Long teacherId,
            LocalDate attendanceDate);

    boolean existsByTeacherSubjectAndAttendanceDateAndStartTime(
            TeacherSubject teacherSubject,
            LocalDate attendanceDate,
            java.time.LocalTime startTime);
}