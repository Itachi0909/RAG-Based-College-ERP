package com.rcet.backend.dto.response;

import com.rcet.backend.entity.BloodGroup;
import com.rcet.backend.entity.Gender;
import com.rcet.backend.entity.GuardianRelation;
import com.rcet.backend.entity.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    // =====================================
    // Basic Information
    // =====================================

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String phone;

    // =====================================
    // Personal Information
    // =====================================

    private Gender gender;

    private LocalDate dateOfBirth;

    private BloodGroup bloodGroup;

    private String photoUrl;

    // =====================================
    // Academic Information
    // =====================================

    private String enrollmentNumber;

    private String registrationNumber;

    private String rollNumber;

    private Integer admissionYear;

    private StudentStatus status;

    // =====================================
    // Address
    // =====================================

    private String currentAddress;

    private String currentCity;

    private String currentState;

    private String currentPincode;

    private String permanentAddress;

    private String permanentCity;

    private String permanentState;

    private String permanentPincode;

    // =====================================
    // Guardian
    // =====================================

    private String guardianName;

    private String guardianPhone;

    private GuardianRelation guardianRelation;

    // =====================================
    // Branch
    // =====================================

    private Long branchId;

    private String branchName;

    // =====================================
    // Semester
    // =====================================

    private Long semesterId;

    private Integer semesterNumber;

    // =====================================
    // Status
    // =====================================

    private boolean active;
}