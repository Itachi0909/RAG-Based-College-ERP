package com.rcet.backend.service;

import com.rcet.backend.dto.request.AttendanceRequest;
import com.rcet.backend.dto.response.AttendanceResponse;

import java.util.List;

public interface AttendanceService {

    AttendanceResponse createAttendance(AttendanceRequest request);

    List<AttendanceResponse> getAllAttendance();

    AttendanceResponse getAttendanceById(Long id);

    AttendanceResponse updateAttendance(Long id,
                                        AttendanceRequest request);

    void deleteAttendance(Long id);
}