package com.rcet.backend.dto.response;

import com.rcet.backend.entity.Designation;
import com.rcet.backend.entity.Gender;
import com.rcet.backend.entity.TeacherStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {

    private Long id;

    // ==========================
    // Personal Information
    // ==========================

    private String firstName;
    private String middleName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String photoUrl;

    // ==========================
    // Contact Information
    // ==========================

    private String email;
    private String phone;

    // ==========================
    // Professional Information
    // ==========================

    private String employeeId;
    private String qualification;
    private Designation designation;
    private TeacherStatus status;
    private LocalDate joiningDate;

    // ==========================
    // Department Details
    // ==========================

    private Long departmentId;
    private String departmentName;

    // ==========================
    // Branch Details
    // ==========================

    private Long branchId;
    private String branchName;

    // ==========================
    // Status
    // ==========================

    private Boolean active;
}