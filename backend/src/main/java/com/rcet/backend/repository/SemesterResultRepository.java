package com.rcet.backend.repository;

import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.SemesterResult;
import com.rcet.backend.entity.Student;
import com.rcet.backend.enums.ResultStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterResultRepository
        extends JpaRepository<SemesterResult, Long> {

    Optional<SemesterResult> findByStudentAndSemester(
            Student student,
            Semester semester
    );
    Optional<SemesterResult> findByStudentAndStatus(
            Student student,
            ResultStatus status
    );

    List<SemesterResult> findByStudent(
            Student student
    );

    List<SemesterResult> findBySemester(
            Semester semester
    );
    List<SemesterResult> findByStatus(
            ResultStatus status
    );
}