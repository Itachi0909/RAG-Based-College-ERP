package com.rcet.backend.mapper;

import com.rcet.backend.dto.response.AttendanceResponse;
import com.rcet.backend.dto.response.AttendanceSessionResponse;
import com.rcet.backend.dto.response.StudentAttendanceViewResponse;
import com.rcet.backend.entity.Attendance;
import com.rcet.backend.entity.AttendanceSession;
import com.rcet.backend.entity.Student;

public class AttendanceMapper {

    private AttendanceMapper() {
    }

    // ==========================================
    // Attendance Session
    // ==========================================

    public static AttendanceSessionResponse
    toAttendanceSessionResponse(
            AttendanceSession session) {

        return new AttendanceSessionResponse(

                session.getId(),

                session.getTeacherSubject().getId(),

                session.getTeacherSubject()
                        .getTeacher()
                        .getFirstName()
                        + " "
                        +
                        session.getTeacherSubject()
                                .getTeacher()
                                .getLastName(),

                session.getTeacherSubject()
                        .getSemesterSubject()
                        .getSubject()
                        .getSubjectCode(),

                session.getTeacherSubject()
                        .getSemesterSubject()
                        .getSubject()
                        .getSubjectName(),

                session.getTeacherSubject()
                        .getSemesterSubject()
                        .getSemester()
                        .getSemesterNumber(),

                session.getAttendanceDate(),

                session.getStartTime(),

                session.getEndTime(),

                session.getTopic(),

                session.getRemarks(),

                session.getStatus()
        );
    }

    // ==========================================
    // Attendance
    // ==========================================

    public static AttendanceResponse
    toAttendanceResponse(
            Attendance attendance) {

        return new AttendanceResponse(

                attendance.getId(),

                attendance.getAttendanceSession().getId(),

                attendance.getStudent().getId(),

                attendance.getStudent()
                        .getEnrollmentNumber(),

                attendance.getStudent()
                        .getRollNumber(),

                attendance.getStudent()
                        .getFirstName()
                        + " "
                        +
                        attendance.getStudent()
                                .getLastName(),

                attendance.getAttendanceStatus(),

                attendance.getRemark()
        );
    }
    public static StudentAttendanceViewResponse
    toStudentAttendanceViewResponse(
            Student student) {

        StudentAttendanceViewResponse response =
                new StudentAttendanceViewResponse();

        response.setStudentId(student.getId());

        response.setEnrollmentNumber(
                student.getEnrollmentNumber());

        response.setRollNumber(
                student.getRollNumber());

        response.setStudentName(
                student.getFirstName() + " " +
                        student.getLastName());

        response.setAttendanceStatus(null);

        response.setRemark(null);

        return response;
    }

}