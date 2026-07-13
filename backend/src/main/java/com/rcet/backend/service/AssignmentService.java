package com.rcet.backend.service;

import com.rcet.backend.dto.request.AssignmentRequest;
import com.rcet.backend.dto.response.AssignmentResponse;

import java.util.List;

public interface AssignmentService {

    AssignmentResponse createAssignment(
            AssignmentRequest request);

    List<AssignmentResponse> getAllAssignments();

    AssignmentResponse getAssignmentById(
            Long id);

    AssignmentResponse updateAssignment(
            Long id,
            AssignmentRequest request);

    void deleteAssignment(
            Long id);

    List<AssignmentResponse> getAssignmentsByStudent(
            Long studentId);

    List<AssignmentResponse> getAssignmentsByTeacher(
            Long teacherId);
}