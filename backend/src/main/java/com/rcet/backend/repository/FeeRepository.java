package com.rcet.backend.repository;

import com.rcet.backend.entity.Fee;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

    boolean existsByStudentAndSemester(
            Student student,
            Semester semester
    );

    Optional<Fee> findByStudentAndSemester(
            Student student,
            Semester semester
    );

    List<Fee> findByStudentAndActiveTrue(
            Student student
    );

    List<Fee> findBySemesterAndActiveTrue(
            Semester semester
    );

    Optional<Fee> findByIdAndActiveTrue(
            Long id
    );

    List<Fee> findByActiveTrue();
}