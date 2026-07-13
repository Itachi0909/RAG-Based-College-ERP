package com.rcet.backend.service;

import com.rcet.backend.dto.request.TeacherRequest;
import com.rcet.backend.dto.response.TeacherResponse;

import java.util.List;

public interface TeacherService {

    TeacherResponse createTeacher(TeacherRequest request);

    TeacherResponse getTeacherById(Long id);

    List<TeacherResponse> getAllTeachers();

    TeacherResponse updateTeacher(Long id, TeacherRequest request);

    void deleteTeacher(Long id);
}