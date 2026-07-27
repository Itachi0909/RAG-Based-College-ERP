package com.rcet.backend.controller.assignmentsubmission;

import com.rcet.backend.dto.request.AssignmentEvaluationRequest;
import com.rcet.backend.dto.request.AssignmentSubmissionRequest;
import com.rcet.backend.dto.response.AssignmentSubmissionResponse;
import com.rcet.backend.service.AssignmentSubmissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/assignment-submissions")
public class AssignmentSubmissionController {

    private final AssignmentSubmissionService assignmentSubmissionService;

    public AssignmentSubmissionController(
            AssignmentSubmissionService assignmentSubmissionService) {

        this.assignmentSubmissionService = assignmentSubmissionService;
    }

    @PostMapping
    public ResponseEntity<AssignmentSubmissionResponse> submitAssignment(
            @Valid @RequestBody AssignmentSubmissionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assignmentSubmissionService.submitAssignment(request));
    }

    @GetMapping("/student")
    public ResponseEntity<List<AssignmentSubmissionResponse>>
    getStudentSubmissions() {

        return ResponseEntity.ok(
                assignmentSubmissionService
                        .getStudentSubmissions());
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<AssignmentSubmissionResponse>>
    getAssignmentSubmissions(
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                assignmentSubmissionService
                        .getAssignmentSubmissions(assignmentId));
    }

    @PutMapping("/{submissionId}/evaluate")
    public ResponseEntity<AssignmentSubmissionResponse>
    evaluateSubmission(
            @PathVariable Long submissionId,
            @Valid @RequestBody AssignmentEvaluationRequest request) {

        return ResponseEntity.ok(
                assignmentSubmissionService
                        .evaluateSubmission(submissionId, request));
    }

    @DeleteMapping("/{submissionId}")
    public ResponseEntity<String> deleteSubmission(
            @PathVariable Long submissionId) {

        assignmentSubmissionService.deleteSubmission(submissionId);

        return ResponseEntity.ok(
                "Assignment submission deleted successfully.");
    }
}