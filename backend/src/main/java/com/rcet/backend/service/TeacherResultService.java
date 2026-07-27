package com.rcet.backend.service;

import com.rcet.backend.dto.request.MarksBulkRequest;
import com.rcet.backend.dto.response.MarksBulkResponse;
import com.rcet.backend.dto.response.StudentMarksResponse;
import com.rcet.backend.dto.response.TeacherSubjectResultResponse;

import java.util.List;

public interface TeacherResultService {

    List<TeacherSubjectResultResponse> getMySubjects();

    List<StudentMarksResponse> getStudentsForMarks(
            Long teacherSubjectId
    );

    MarksBulkResponse saveMarks(
            MarksBulkRequest request
    );

    void submitMarks(
            Long teacherSubjectId
    );

    List<StudentMarksResponse> viewMarks(
            Long teacherSubjectId
    );
}