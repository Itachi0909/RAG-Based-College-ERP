package com.rcet.backend.repository;

import com.rcet.backend.entity.DayOfWeek;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.TeacherSubject;
import com.rcet.backend.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimetableRepository
        extends JpaRepository<Timetable, Long> {

    boolean existsByTeacherSubjectAndDayOfWeekAndStartTime(
            TeacherSubject teacherSubject,
            DayOfWeek dayOfWeek,
            LocalTime startTime
    );

    List<Timetable> findByTeacherSubject_SemesterSubject_Semester_IdAndActiveTrue(
            Long semesterId
    );

    List<Timetable> findByTeacherSubjectAndActiveTrue(
            TeacherSubject teacherSubject
    );

    List<Timetable> findByTeacherSubject_TeacherAndActiveTrue(
            Teacher teacher
    );

    List<Timetable> findByTeacherSubject_SemesterSubject_SemesterAndActiveTrue(
            Semester semester
    );

    List<Timetable> findByDayOfWeekAndActiveTrue(
            DayOfWeek dayOfWeek
    );

    Optional<Timetable> findByIdAndActiveTrue(Long id);

}