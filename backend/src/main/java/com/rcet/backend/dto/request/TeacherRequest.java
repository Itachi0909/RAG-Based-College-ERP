package com.rcet.backend.dto.request;

import com.rcet.backend.entity.Designation;
import com.rcet.backend.entity.Gender;
import com.rcet.backend.entity.TeacherStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherRequest {

    // ==========================
    // Personal Information
    // ==========================

    @NotBlank(message = "First name is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    private String photoUrl;

    // ==========================
    // Contact Information
    // ==========================

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    private String phone;

    // ==========================
    // Professional Information
    // ==========================

    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotBlank(message = "Qualification is required")
    private String qualification;

    @NotNull(message = "Designation is required")
    private Designation designation;

    @NotNull(message = "Teacher status is required")
    private TeacherStatus status;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

    // ==========================
    // Relationships
    // ==========================

    @NotNull(message = "Department is required")
    private Long departmentId;

    @NotNull(message = "Branch is required")
    private Long branchId;

    // ==========================
    // Status
    // ==========================

    private Boolean active;
}