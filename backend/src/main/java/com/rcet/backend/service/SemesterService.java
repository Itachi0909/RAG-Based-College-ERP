package com.rcet.backend.service;

import com.rcet.backend.dto.request.SemesterRequest;
import com.rcet.backend.dto.response.SemesterResponse;

import java.util.List;

public interface SemesterService {

    SemesterResponse createSemester(SemesterRequest request);

    List<SemesterResponse> getAllSemesters();

    SemesterResponse getSemesterById(Long id);

    SemesterResponse updateSemester(Long id, SemesterRequest request);

    void deleteSemester(Long id);

}