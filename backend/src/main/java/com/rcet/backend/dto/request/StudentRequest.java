package com.rcet.backend.dto.request;

import com.rcet.backend.entity.BloodGroup;
import com.rcet.backend.entity.Gender;
import com.rcet.backend.entity.GuardianRelation;
import com.rcet.backend.entity.StudentStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentRequest {

    // Personal Information
    @NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    private BloodGroup bloodGroup;

    private String photoUrl;

    // Contact Information
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    // Academic Information
    @NotBlank(message = "Enrollment number is required")
    private String enrollmentNumber;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    @NotBlank(message = "Roll number is required")
    private String rollNumber;

    @NotNull(message = "Admission year is required")
    private Integer admissionYear;

    @NotNull(message = "Student status is required")
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
    @NotBlank(message = "Guardian name is required")
    private String guardianName;

    @NotBlank(message = "Guardian phone is required")
    private String guardianPhone;

    @NotNull(message = "Guardian relation is required")
    private GuardianRelation guardianRelation;

    // Relationships
    @NotNull(message = "Branch is required")
    private Long branchId;

    @NotNull(message = "Semester is required")
    private Long semesterId;

    // Status
    private Boolean active;
}