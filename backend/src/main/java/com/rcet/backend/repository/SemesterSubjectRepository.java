package com.rcet.backend.repository;

import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.SemesterSubject;
import com.rcet.backend.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterSubjectRepository
        extends JpaRepository<SemesterSubject, Long> {

    List<SemesterSubject> findByActiveTrue();

    List<SemesterSubject> findBySemester(Semester semester);

    List<SemesterSubject> findBySemesterAndActiveTrue(
            Semester semester
    );

    List<SemesterSubject> findBySemesterId(Long semesterId);

    List<SemesterSubject> findBySemesterIdAndActiveTrue(
            Long semesterId
    );

    Optional<SemesterSubject> findByIdAndActiveTrue(Long id);

    Optional<SemesterSubject> findBySemesterAndSubject(
            Semester semester,
            Subject subject
    );

    boolean existsBySemesterAndSubject(
            Semester semester,
            Subject subject
    );

    boolean existsBySemesterAndSubjectAndActiveTrue(
            Semester semester,
            Subject subject
    );
}