package com.rcet.backend.mapper;

import com.rcet.backend.dto.request.TeacherRequest;
import com.rcet.backend.dto.response.TeacherResponse;
import com.rcet.backend.entity.Teacher;

public class TeacherMapper {

    private TeacherMapper() {
        // Prevent object creation
    }

    /**
     * Convert Request DTO to Entity
     */
    public static Teacher toEntity(TeacherRequest request) {

        Teacher teacher = new Teacher();

        teacher.setFirstName(request.getFirstName());
        teacher.setMiddleName(request.getMiddleName());
        teacher.setLastName(request.getLastName());

        teacher.setGender(request.getGender());
        teacher.setDateOfBirth(request.getDateOfBirth());

        teacher.setPhotoUrl(request.getPhotoUrl());

        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());

        teacher.setEmployeeId(request.getEmployeeId());
        teacher.setQualification(request.getQualification());
        teacher.setDesignation(request.getDesignation());
        teacher.setStatus(request.getStatus());
        teacher.setJoiningDate(request.getJoiningDate());

        teacher.setActive(true);

        return teacher;
    }

    /**
     * Convert Entity to Response DTO
     */
    public static TeacherResponse toResponse(Teacher teacher) {

        return TeacherResponse.builder()
                .id(teacher.getId())
                .employeeId(teacher.getEmployeeId())
                .firstName(teacher.getFirstName())
                .middleName(teacher.getMiddleName())
                .lastName(teacher.getLastName())
                .email(teacher.getEmail())
                .phone(teacher.getPhone())
                .qualification(teacher.getQualification())
                .designation(teacher.getDesignation())
                .status(teacher.getStatus())

                .departmentId(teacher.getDepartment().getId())
                .departmentName(teacher.getDepartment().getName())

                .branchId(teacher.getBranch().getId())
                .branchName(teacher.getBranch().getName())

                .active(teacher.isActive())
                .build();
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(
            Teacher teacher,
            TeacherRequest request) {

        teacher.setFirstName(request.getFirstName());
        teacher.setMiddleName(request.getMiddleName());
        teacher.setLastName(request.getLastName());

        teacher.setGender(request.getGender());
        teacher.setDateOfBirth(request.getDateOfBirth());

        teacher.setPhotoUrl(request.getPhotoUrl());

        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());

        teacher.setEmployeeId(request.getEmployeeId());
        teacher.setQualification(request.getQualification());
        teacher.setDesignation(request.getDesignation());
        teacher.setStatus(request.getStatus());
        teacher.setJoiningDate(request.getJoiningDate());
    }
}