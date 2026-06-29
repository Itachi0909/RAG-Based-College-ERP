package com.rcet.backend.service;

import com.rcet.backend.dto.request.SubjectRequest;
import com.rcet.backend.dto.response.SubjectResponse;

import java.util.List;

public interface SubjectService {

    SubjectResponse createSubject(SubjectRequest request);

    List<SubjectResponse> getAllSubjects();

    SubjectResponse getSubjectById(Long id);

    SubjectResponse updateSubject(Long id, SubjectRequest request);

    void deleteSubject(Long id);

}