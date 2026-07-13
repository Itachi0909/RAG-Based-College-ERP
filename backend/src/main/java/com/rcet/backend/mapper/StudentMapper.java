package com.rcet.backend.mapper;

import com.rcet.backend.dto.request.StudentRequest;
import com.rcet.backend.dto.response.StudentResponse;
import com.rcet.backend.entity.Student;

public class StudentMapper {

    private StudentMapper() {
    }

    /**
     * Convert Request DTO to Entity
     */
    public static Student toEntity(StudentRequest request) {

        Student student = new Student();

        student.setFirstName(request.getFirstName());
        student.setMiddleName(request.getMiddleName());
        student.setLastName(request.getLastName());

        student.setGender(request.getGender());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setBloodGroup(request.getBloodGroup());
        student.setPhotoUrl(request.getPhotoUrl());

        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());

        student.setEnrollmentNumber(request.getEnrollmentNumber());
        student.setRegistrationNumber(request.getRegistrationNumber());
        student.setRollNumber(request.getRollNumber());
        student.setAdmissionYear(request.getAdmissionYear());
        student.setStatus(request.getStatus());

        student.setCurrentAddress(request.getCurrentAddress());
        student.setCurrentCity(request.getCurrentCity());
        student.setCurrentState(request.getCurrentState());
        student.setCurrentPincode(request.getCurrentPincode());

        student.setPermanentAddress(request.getPermanentAddress());
        student.setPermanentCity(request.getPermanentCity());
        student.setPermanentState(request.getPermanentState());
        student.setPermanentPincode(request.getPermanentPincode());

        student.setGuardianName(request.getGuardianName());
        student.setGuardianPhone(request.getGuardianPhone());
        student.setGuardianRelation(request.getGuardianRelation());

        student.setActive(true);

        return student;
    }

    /**
     * Convert Entity to Response DTO
     */
    public static StudentResponse toResponse(Student student) {

        return StudentResponse.builder()

                .id(student.getId())

                .firstName(student.getFirstName())
                .middleName(student.getMiddleName())
                .lastName(student.getLastName())

                .email(student.getEmail())
                .phone(student.getPhone())

                .gender(student.getGender())
                .dateOfBirth(student.getDateOfBirth())
                .bloodGroup(student.getBloodGroup())
                .photoUrl(student.getPhotoUrl())

                .enrollmentNumber(student.getEnrollmentNumber())
                .registrationNumber(student.getRegistrationNumber())
                .rollNumber(student.getRollNumber())
                .admissionYear(student.getAdmissionYear())
                .status(student.getStatus())

                .currentAddress(student.getCurrentAddress())
                .currentCity(student.getCurrentCity())
                .currentState(student.getCurrentState())
                .currentPincode(student.getCurrentPincode())

                .permanentAddress(student.getPermanentAddress())
                .permanentCity(student.getPermanentCity())
                .permanentState(student.getPermanentState())
                .permanentPincode(student.getPermanentPincode())

                .guardianName(student.getGuardianName())
                .guardianPhone(student.getGuardianPhone())
                .guardianRelation(student.getGuardianRelation())

                .branchId(student.getBranch().getId())
                .branchName(student.getBranch().getName())

                .semesterId(student.getSemester().getId())
                .semesterNumber(student.getSemester().getSemesterNumber())

                .active(student.isActive())

                .build();
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(
            Student student,
            StudentRequest request) {

        student.setFirstName(request.getFirstName());
        student.setMiddleName(request.getMiddleName());
        student.setLastName(request.getLastName());

        student.setGender(request.getGender());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setBloodGroup(request.getBloodGroup());
        student.setPhotoUrl(request.getPhotoUrl());

        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());

        student.setEnrollmentNumber(request.getEnrollmentNumber());
        student.setRegistrationNumber(request.getRegistrationNumber());
        student.setRollNumber(request.getRollNumber());
        student.setAdmissionYear(request.getAdmissionYear());
        student.setStatus(request.getStatus());

        student.setCurrentAddress(request.getCurrentAddress());
        student.setCurrentCity(request.getCurrentCity());
        student.setCurrentState(request.getCurrentState());
        student.setCurrentPincode(request.getCurrentPincode());

        student.setPermanentAddress(request.getPermanentAddress());
        student.setPermanentCity(request.getPermanentCity());
        student.setPermanentState(request.getPermanentState());
        student.setPermanentPincode(request.getPermanentPincode());

        student.setGuardianName(request.getGuardianName());
        student.setGuardianPhone(request.getGuardianPhone());
        student.setGuardianRelation(request.getGuardianRelation());
    }
}