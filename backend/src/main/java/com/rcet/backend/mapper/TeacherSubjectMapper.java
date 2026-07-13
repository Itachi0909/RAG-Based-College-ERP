package com.rcet.backend.mapper;

import com.rcet.backend.dto.response.TeacherSubjectResponse;
import com.rcet.backend.entity.TeacherSubject;

public class TeacherSubjectMapper {

    private TeacherSubjectMapper() {
    }

    /**
     * Convert Entity to Response DTO
     */
    public static TeacherSubjectResponse toResponse(
            TeacherSubject teacherSubject) {

        return TeacherSubjectResponse.builder()

                .id(teacherSubject.getId())

                // =========================
                // Teacher
                // =========================

                .teacherId(
                        teacherSubject.getTeacher().getId())

                .employeeId(
                        teacherSubject.getTeacher().getEmployeeId())

                .teacherName(
                        teacherSubject.getTeacher().getFirstName()
                                + " "
                                + teacherSubject.getTeacher().getLastName())

                // =========================
                // Semester Subject
                // =========================

                .semesterSubjectId(
                        teacherSubject.getSemesterSubject().getId())

                // =========================
                // Semester
                // =========================

                .semesterId(
                        teacherSubject.getSemesterSubject()
                                .getSemester()
                                .getId())

                .semesterNumber(
                        teacherSubject.getSemesterSubject()
                                .getSemester()
                                .getSemesterNumber())

                // =========================
                // Subject
                // =========================

                .subjectId(
                        teacherSubject.getSemesterSubject()
                                .getSubject()
                                .getId())

                .subjectCode(
                        teacherSubject.getSemesterSubject()
                                .getSubject()
                                .getSubjectCode())

                .subjectName(
                        teacherSubject.getSemesterSubject()
                                .getSubject()
                                .getSubjectName())

                // =========================
                // Branch
                // =========================

                .branchId(
                        teacherSubject.getSemesterSubject()
                                .getSemester()
                                .getBranch()
                                .getId())

                .branchName(
                        teacherSubject.getSemesterSubject()
                                .getSemester()
                                .getBranch()
                                .getName())

                // =========================
                // Department
                // =========================

                .departmentId(
                        teacherSubject.getSemesterSubject()
                                .getSemester()
                                .getBranch()
                                .getDepartment()
                                .getId())

                .departmentName(
                        teacherSubject.getSemesterSubject()
                                .getSemester()
                                .getBranch()
                                .getDepartment()
                                .getName())

                .build();
    }
}