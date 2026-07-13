package com.rcet.backend.repository;

import com.rcet.backend.entity.Assignment;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository
        extends JpaRepository<Assignment, Long> {

    Optional<Assignment> findByIdAndActiveTrue(Long id);

    boolean existsByIdAndActiveTrue(Long id);

    List<Assignment> findByActiveTrue();

    List<Assignment> findByTeacherSubjectAndActiveTrue(
            TeacherSubject teacherSubject
    );

    List<Assignment> findByTeacherSubject_TeacherAndActiveTrue(
            Teacher teacher
    );

    List<Assignment> findByTeacherSubject_SemesterSubject_Semester_IdAndActiveTrue(
            Long semesterId
    );
}