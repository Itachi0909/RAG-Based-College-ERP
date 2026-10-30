package com.rcet.backend.repository;

import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.SemesterSubject;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherSubjectRepository
        extends JpaRepository<TeacherSubject, Long> {

    // Active assignments
    List<TeacherSubject> findByActiveTrue();

    Optional<TeacherSubject> findByIdAndActiveTrue(Long id);

    // Teacher assignments
    List<TeacherSubject> findByTeacher(Teacher teacher);

    List<TeacherSubject> findByTeacherAndActiveTrue(
            Teacher teacher
    );

    Optional<TeacherSubject> findByIdAndTeacher(
            Long id,
            Teacher teacher
    );

    Optional<TeacherSubject> findByIdAndTeacherAndActiveTrue(
            Long id,
            Teacher teacher
    );

    // Semester subject assignments
    List<TeacherSubject> findByTeacherAndSemesterSubject(
            Teacher teacher,
            SemesterSubject semesterSubject
    );

    List<TeacherSubject> findByTeacherAndSemesterSubjectAndActiveTrue(
            Teacher teacher,
            SemesterSubject semesterSubject
    );

    // Find assignments through SemesterSubject -> Semester
    List<TeacherSubject> findBySemesterSubject_Semester(
            Semester semester
    );

    List<TeacherSubject> findBySemesterSubject_SemesterAndActiveTrue(
            Semester semester
    );

    // Duplicate checks
    boolean existsByTeacherAndSemesterSubject(
            Teacher teacher,
            SemesterSubject semesterSubject
    );

    boolean existsByTeacherAndSemesterSubjectAndActiveTrue(
            Teacher teacher,
            SemesterSubject semesterSubject
    );

    boolean existsByTeacherIdAndSemesterSubjectIdAndActiveTrue(
            Long teacherId,
            Long semesterSubjectId
    );

    // ID-based searches
    List<TeacherSubject> findByTeacherIdAndActiveTrue(
            Long teacherId
    );
}