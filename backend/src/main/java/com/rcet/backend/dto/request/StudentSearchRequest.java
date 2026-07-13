package com.rcet.backend.dto.request;

import com.rcet.backend.entity.StudentStatus;
import lombok.Data;

@Data
public class StudentSearchRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String enrollmentNumber;

    private Long branchId;

    private Long semesterId;

    private Integer admissionYear;

    private StudentStatus status;
}