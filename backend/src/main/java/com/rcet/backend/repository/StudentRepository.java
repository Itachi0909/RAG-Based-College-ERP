package com.rcet.backend.repository;

import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.rcet.backend.entity.Branch;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {

    List<Student> findBySemester(
            Semester semester);

    List<Student> findByBranchAndSemester(
            Branch branch,
            Semester semester);

    List<Student> findByActiveTrue();


    long countByBranchAndSemester(
            Branch branch,
            Semester semester);

    long countByBranchAndSemesterAndActiveTrue(
            Branch branch,
            Semester semester
    );



    Optional<Student> findByEmail(String email);

    Optional<Student> findByPhone(String phone);

    Optional<Student> findByEnrollmentNumber(String enrollmentNumber);

    Optional<Student> findByRegistrationNumber(String registrationNumber);

    Optional<Student> findByRollNumber(String rollNumber);

    Optional<Student> findByIdAndActiveTrue(Long id);

    Optional<Student> findByUser(User user);



    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByEnrollmentNumber(String enrollmentNumber);

    boolean existsByRegistrationNumber(String registrationNumber);

    boolean existsByRollNumber(String rollNumber);


    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByEnrollmentNumberAndIdNot(String enrollmentNumber, Long id);

    boolean existsByRegistrationNumberAndIdNot(String registrationNumber, Long id);

    boolean existsByRollNumberAndIdNot(String rollNumber, Long id);

}