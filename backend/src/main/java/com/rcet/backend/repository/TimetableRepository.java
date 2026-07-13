package com.rcet.backend.repository;

import com.rcet.backend.entity.DayOfWeek;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.TeacherSubject;
import com.rcet.backend.entity.Timetable;
import com.rcet.backend.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimetableRepository
        extends JpaRepository<Timetable, Long> {

    boolean existsByTeacherSubjectAndDayOfWeekAndStartTime(
            TeacherSubject teacherSubject,
            DayOfWeek dayOfWeek,
            LocalTime startTime
    );

    List<Timetable> findByTeacherSubject_SemesterSubject_Semester_Id(
            Long semesterId
    );

    List<Timetable> findByTeacherSubject(
            TeacherSubject teacherSubject
    );

    List<Timetable> findByTeacherSubject_Teacher(
            Teacher teacher
    );

    List<Timetable> findByTeacherSubject_SemesterSubject_Semester(
            Semester semester
    );

    List<Timetable> findByDayOfWeek(
            DayOfWeek dayOfWeek
    );
}