package com.rcet.backend.repository;

import com.rcet.backend.entity.Branch;
import com.rcet.backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByNameAndDepartment(String name, Department department);

    boolean existsByNameAndDepartment(String name, Department department);

    List<Branch> findByDepartment(Department department);

}