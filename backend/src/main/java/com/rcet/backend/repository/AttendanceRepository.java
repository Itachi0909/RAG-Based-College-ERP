package com.rcet.backend.repository;

import com.rcet.backend.entity.Attendance;
import com.rcet.backend.entity.AttendanceSession;
import com.rcet.backend.entity.Student;
import com.rcet.backend.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByAttendanceSession(
            AttendanceSession attendanceSession);

    List<Attendance> findByStudent(
            Student student);

    List<Attendance> findByStudentAndAttendanceStatus(
            Student student,
            AttendanceStatus attendanceStatus);

    boolean existsByAttendanceSessionAndStudent(
            AttendanceSession attendanceSession,
            Student student);

    Optional<Attendance> findByAttendanceSessionAndStudent(
            AttendanceSession attendanceSession,
            Student student);
}