package com.rcet.backend.specification;

import com.rcet.backend.dto.request.StudentSearchRequest;
import com.rcet.backend.entity.Student;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class StudentSpecification {

    private StudentSpecification() {
    }

    public static Specification<Student> search(
            StudentSearchRequest request) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getFirstName() != null &&
                    !request.getFirstName().isBlank()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("firstName")),
                                "%" + request.getFirstName().toLowerCase() + "%"
                        )
                );
            }

            if (request.getLastName() != null &&
                    !request.getLastName().isBlank()) {

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("lastName")),
                                "%" + request.getLastName().toLowerCase() + "%"
                        )
                );
            }

            if (request.getEmail() != null &&
                    !request.getEmail().isBlank()) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("email"),
                                request.getEmail()
                        )
                );
            }

            if (request.getEnrollmentNumber() != null &&
                    !request.getEnrollmentNumber().isBlank()) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("enrollmentNumber"),
                                request.getEnrollmentNumber()
                        )
                );
            }

            if (request.getBranchId() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("branch").get("id"),
                                request.getBranchId()
                        )
                );
            }

            if (request.getSemesterId() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("semester").get("id"),
                                request.getSemesterId()
                        )
                );
            }

            if (request.getAdmissionYear() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("admissionYear"),
                                request.getAdmissionYear()
                        )
                );
            }

            if (request.getStatus() != null) {

                predicates.add(
                        criteriaBuilder.equal(
                                root.get("status"),
                                request.getStatus()
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}