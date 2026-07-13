package com.rcet.backend.repository;

import com.rcet.backend.entity.Branch;
import com.rcet.backend.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {

    boolean existsBySemesterNumberAndBranch(Integer semesterNumber, Branch branch);

    List<Semester> findByBranch(Branch branch);

    List<Semester> findByBranchOrderBySemesterNumberAsc(
            Branch branch
    );

    Optional<Semester> findBySemesterNumberAndBranch(
            Integer semesterNumber,
            Branch branch);

}