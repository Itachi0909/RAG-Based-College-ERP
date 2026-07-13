package com.rcet.backend.service;

import com.rcet.backend.dto.request.AttendanceSessionRequest;
import com.rcet.backend.dto.response.AttendanceSessionResponse;

import java.util.List;

public interface AttendanceSessionService {

    AttendanceSessionResponse createAttendanceSession(
            AttendanceSessionRequest request);

    AttendanceSessionResponse getAttendanceSessionById(
            Long id);

    List<AttendanceSessionResponse> getAllAttendanceSessions();

    List<AttendanceSessionResponse> getAttendanceSessionsByTeacher(
            Long teacherId);

    AttendanceSessionResponse updateAttendanceSession(
            Long id,
            AttendanceSessionRequest request);

    void deleteAttendanceSession(
            Long id);
}