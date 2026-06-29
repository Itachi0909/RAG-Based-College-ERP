package com.rcet.backend.repository;

import com.rcet.backend.entity.Attendance;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByStudentAndTeacherSubjectAndAttendanceDateAndLectureNumber(
            Student student,
            TeacherSubject teacherSubject,
            LocalDate attendanceDate,
            Integer lectureNumber
    );

    List<Attendance> findByStudent(Student student);

    List<Attendance> findByTeacherSubject(TeacherSubject teacherSubject);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    List<Attendance> findByStudentAndAttendanceDate(
            Student student,
            LocalDate attendanceDate
    );
}