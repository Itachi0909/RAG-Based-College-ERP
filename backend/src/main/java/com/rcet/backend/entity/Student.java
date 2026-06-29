package com.rcet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student extends BaseEntity {

    // ==========================
    // Personal Information
    // ==========================

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(length = 50)
    private String middleName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    private String photoUrl;

    // ==========================
    // Contact Information
    // ==========================

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    // ==========================
    // Academic Information
    // ==========================

    @Column(nullable = false, unique = true)
    private String enrollmentNumber;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @Column(nullable = false, unique = true)
    private String rollNumber;

    @Column(nullable = false)
    private Integer admissionYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudentStatus status;

    // ==========================
    // Current Address
    // ==========================

    private String currentAddress;

    private String currentCity;

    private String currentState;

    private String currentPincode;

    // ==========================
    // Permanent Address
    // ==========================

    private String permanentAddress;

    private String permanentCity;

    private String permanentState;

    private String permanentPincode;

    // ==========================
    // Guardian Information
    // ==========================

    @Column(nullable = false)
    private String guardianName;

    @Column(nullable = false)
    private String guardianPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GuardianRelation guardianRelation;

    // ==========================
    // Relationships
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

}