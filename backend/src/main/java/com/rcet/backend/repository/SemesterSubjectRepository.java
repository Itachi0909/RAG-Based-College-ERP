package com.rcet.backend.repository;

import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.SemesterSubject;
import com.rcet.backend.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface SemesterSubjectRepository
        extends JpaRepository<SemesterSubject, Long> {

    boolean existsBySemesterAndSubject(
            Semester semester,
            Subject subject);

    Optional<SemesterSubject> findBySemesterAndSubject(
            Semester semester,
            Subject subject);

    Optional<SemesterSubject> findByIdAndActiveTrue(Long id);

    List<SemesterSubject> findByActiveTrue();

    List<SemesterSubject> findBySemester(
            Semester semester);

    List<SemesterSubject> findBySubject(
            Subject subject);
}