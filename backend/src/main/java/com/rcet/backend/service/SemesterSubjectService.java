package com.rcet.backend.service;

import com.rcet.backend.dto.request.SemesterSubjectRequest;
import com.rcet.backend.dto.response.SemesterSubjectResponse;

import java.util.List;

public interface SemesterSubjectService {

    SemesterSubjectResponse createSemesterSubject(
            SemesterSubjectRequest request
    );

    List<SemesterSubjectResponse> getAllSemesterSubjects();

    SemesterSubjectResponse getSemesterSubjectById(
            Long id
    );

    SemesterSubjectResponse updateSemesterSubject(
            Long id,
            SemesterSubjectRequest request
    );

    void deleteSemesterSubject(
            Long id
    );

    List<SemesterSubjectResponse> getSemesterSubjectsBySemester(
            Long semesterId
    );
}