package com.rcet.backend.repository;

import com.rcet.backend.entity.SemesterResult;
import com.rcet.backend.entity.SemesterSubject;
import com.rcet.backend.entity.SubjectResult;
import com.rcet.backend.enums.ResultStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectResultRepository
        extends JpaRepository<SubjectResult, Long> {

    List<SubjectResult> findBySemesterResult(
            SemesterResult semesterResult
    );
    List<SubjectResult> findBySemesterSubject(
            SemesterSubject semesterSubject
    );

    Optional<SubjectResult> findBySemesterResultAndSemesterSubject(
            SemesterResult semesterResult,
            SemesterSubject semesterSubject
    );
    long countBySemesterResultAndStatus(
            SemesterResult semesterResult,
            ResultStatus status
    );
}