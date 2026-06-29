package com.rcet.backend.dto.response;

import com.rcet.backend.entity.BloodGroup;
import com.rcet.backend.entity.Gender;
import com.rcet.backend.entity.GuardianRelation;
import com.rcet.backend.entity.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Long id;

    // Personal Information
    private String firstName;
    private String middleName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private BloodGroup bloodGroup;
    private String photoUrl;

    // Contact Information
    private String email;
    private String phone;

    // Academic Information
    private String enrollmentNumber;
    private String registrationNumber;
    private String rollNumber;
    private Integer admissionYear;
    private StudentStatus status;

    // Current Address
    private String currentAddress;
    private String currentCity;
    private String currentState;
    private String currentPincode;

    // Permanent Address
    private String permanentAddress;
    private String permanentCity;
    private String permanentState;
    private String permanentPincode;

    // Guardian Information
    private String guardianName;
    private String guardianPhone;
    private GuardianRelation guardianRelation;

    // Branch Details
    private Long branchId;
    private String branchName;

    // Semester Details
    private Long semesterId;
    private Integer semesterNumber;

    // Status
    private Boolean active;
}