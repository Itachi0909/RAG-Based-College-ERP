package com.rcet.backend.repository;

import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {


    List<Teacher> findByActiveTrue();

    Optional<Teacher> findByEmail(String email);

    Optional<Teacher> findByPhone(String phone);

    Optional<Teacher> findByEmployeeId(String employeeId);

    Optional<Teacher> findByIdAndActiveTrue(Long id);

    Optional<Teacher> findByUser(User user);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByEmployeeId(String employeeId);
}