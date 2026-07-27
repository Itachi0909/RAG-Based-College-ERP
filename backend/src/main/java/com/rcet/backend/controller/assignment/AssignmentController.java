package com.rcet.backend.controller.assignment;

import com.rcet.backend.dto.request.AssignmentRequest;
import com.rcet.backend.dto.response.AssignmentResponse;
import com.rcet.backend.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(
            AssignmentService assignmentService) {

        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<AssignmentResponse> createAssignment(
            @Valid @RequestBody AssignmentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assignmentService.createAssignment(request));
    }

    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> getAllAssignments() {

        return ResponseEntity.ok(
                assignmentService.getAllAssignments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getAssignmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                assignmentService.getAssignmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentResponse> updateAssignment(
            @PathVariable Long id,
            @Valid @RequestBody AssignmentRequest request) {

        return ResponseEntity.ok(
                assignmentService.updateAssignment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssignment(
            @PathVariable Long id) {

        assignmentService.deleteAssignment(id);

        return ResponseEntity.ok(
                "Assignment deleted successfully.");
    }

    @GetMapping("/student")
    public ResponseEntity<List<AssignmentResponse>>
    getAssignmentsByStudent() {

        return ResponseEntity.ok(
                assignmentService.getAssignmentsByStudent());
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<AssignmentResponse>>
    getAssignmentsByTeacher() {

        return ResponseEntity.ok(
                assignmentService.getAssignmentsByTeacher());
    }
}