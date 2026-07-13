package com.rcet.backend.service;

import com.rcet.backend.dto.request.AssignmentEvaluationRequest;
import com.rcet.backend.dto.request.AssignmentSubmissionRequest;
import com.rcet.backend.dto.response.AssignmentSubmissionResponse;

import java.util.List;

public interface AssignmentSubmissionService {

    AssignmentSubmissionResponse submitAssignment(
            AssignmentSubmissionRequest request);

    List<AssignmentSubmissionResponse> getStudentSubmissions(
            Long studentId);

    List<AssignmentSubmissionResponse> getAssignmentSubmissions(
            Long assignmentId);

    AssignmentSubmissionResponse evaluateSubmission(
            Long submissionId,
            AssignmentEvaluationRequest request);

    void deleteSubmission(
            Long submissionId);
}