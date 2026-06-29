package com.rcet.backend.service;

import com.rcet.backend.dto.request.TeacherSubjectRequest;
import com.rcet.backend.dto.response.TeacherSubjectResponse;

import java.util.List;

public interface TeacherSubjectService {

    TeacherSubjectResponse createTeacherSubject(TeacherSubjectRequest request);

    List<TeacherSubjectResponse> getAllTeacherSubjects();

    TeacherSubjectResponse getTeacherSubjectById(Long id);

    TeacherSubjectResponse updateTeacherSubject(Long id,
                                                TeacherSubjectRequest request);

    void deleteTeacherSubject(Long id);
}