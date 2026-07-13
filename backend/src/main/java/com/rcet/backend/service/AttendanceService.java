package com.rcet.backend.service;

import com.rcet.backend.dto.request.AttendanceBulkRequest;
import com.rcet.backend.dto.response.AttendanceBulkResponse;
import com.rcet.backend.dto.response.AttendanceResponse;
import com.rcet.backend.dto.response.StudentAttendanceViewResponse;

import java.util.List;

public interface AttendanceService {

    AttendanceBulkResponse markAttendance(
            AttendanceBulkRequest request);

    AttendanceBulkResponse updateAttendance(
            AttendanceBulkRequest request);

    AttendanceResponse getAttendanceById(
            Long attendanceId);

    List<AttendanceResponse> getAttendanceBySession(
            Long attendanceSessionId);

    List<AttendanceResponse> getAttendanceByStudent(
            Long studentId);

    List<StudentAttendanceViewResponse> getStudentsForAttendance(
            Long attendanceSessionId);

    void deleteAttendance(
            Long attendanceId);
}