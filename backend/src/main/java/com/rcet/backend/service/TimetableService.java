package com.rcet.backend.service;

import com.rcet.backend.dto.request.TimetableRequest;
import com.rcet.backend.dto.response.TimetableResponse;

import java.util.List;

public interface TimetableService {

    TimetableResponse createTimetable(TimetableRequest request);

    List<TimetableResponse> getAllTimetables();

    TimetableResponse getTimetableById(Long id);

    TimetableResponse updateTimetable(
            Long id,
            TimetableRequest request
    );

    void deleteTimetable(Long id);

    List<TimetableResponse> getTimetableByStudent(Long studentId);

    List<TimetableResponse> getTimetableByTeacher(Long teacherId);

    List<TimetableResponse> getTimetableBySemester(Long semesterId);

    List<TimetableResponse> getCurrentStudentTimetable();

    List<TimetableResponse> getCurrentTeacherTimetable();
}