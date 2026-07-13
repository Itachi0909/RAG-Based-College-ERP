package com.rcet.backend.repository;

import com.rcet.backend.entity.Assignment;
import com.rcet.backend.entity.AssignmentSubmission;
import com.rcet.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentSubmissionRepository
        extends JpaRepository<AssignmentSubmission, Long> {

    boolean existsByAssignmentAndStudent(
            Assignment assignment,
            Student student
    );

    Optional<AssignmentSubmission> findByAssignmentAndStudent(
            Assignment assignment,
            Student student
    );

    List<AssignmentSubmission> findByStudentAndActiveTrue(
            Student student
    );

    List<AssignmentSubmission> findByAssignmentAndActiveTrue(
            Assignment assignment
    );

    Optional<AssignmentSubmission> findByIdAndActiveTrue(
            Long id
    );
}