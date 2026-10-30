package com.rcet.backend.dto.request;

import com.rcet.backend.entity.Designation;
import com.rcet.backend.entity.Gender;
import com.rcet.backend.entity.TeacherStatus;
import jakarta.validation.constraints.*;
import lombok.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherRequest {

    // ==========================
    // Login Information
    // ==========================

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    private String password;

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
    @Past(message = "Date of birth must be in the past.")
    private LocalDate dateOfBirth;

    private String photoUrl;

    // ==========================
    // Contact Information
    // ==========================

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be a valid 10-digit Indian mobile number."
    )
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
    @PastOrPresent(message = "Joining date cannot be in the future.")
    private LocalDate joiningDate;

    // ==========================
    // Relationships
    // ==========================

    @NotNull(message = "Department is required")
    private Long departmentId;

    @NotNull(message = "Branch is required")
    private Long branchId;
}