package com.rcet.backend.repository;

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

    boolean existsByTeacherAndSemesterSubject(
            Teacher teacher,
            SemesterSubject semesterSubject);

    List<TeacherSubject> findByTeacher(
            Teacher teacher);

    List<TeacherSubject> findBySemesterSubject(
            SemesterSubject semesterSubject);

    Optional<TeacherSubject> findByIdAndTeacher(
            Long id,
            Teacher teacher);
}