package com.rcet.backend.controller.semestersubject;

import com.rcet.backend.dto.request.SemesterSubjectRequest;
import com.rcet.backend.dto.response.SemesterSubjectResponse;
import com.rcet.backend.service.SemesterSubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/semester-subjects")
public class SemesterSubjectController {

    private final SemesterSubjectService semesterSubjectService;

    public SemesterSubjectController(SemesterSubjectService semesterSubjectService) {
        this.semesterSubjectService = semesterSubjectService;
    }

    @PostMapping
    public ResponseEntity<SemesterSubjectResponse> createSemesterSubject(
            @Valid @RequestBody SemesterSubjectRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(semesterSubjectService.createSemesterSubject(request));
    }

    @GetMapping
    public ResponseEntity<List<SemesterSubjectResponse>> getAllSemesterSubjects() {

        return ResponseEntity.ok(
                semesterSubjectService.getAllSemesterSubjects()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemesterSubjectResponse> getSemesterSubjectById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                semesterSubjectService.getSemesterSubjectById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemesterSubjectResponse> updateSemesterSubject(
            @PathVariable Long id,
            @Valid @RequestBody SemesterSubjectRequest request) {

        return ResponseEntity.ok(
                semesterSubjectService.updateSemesterSubject(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSemesterSubject(
            @PathVariable Long id) {

        semesterSubjectService.deleteSemesterSubject(id);

        return ResponseEntity.ok("Semester Subject mapping deleted successfully.");
    }

    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<List<SemesterSubjectResponse>>
    getSemesterSubjectsBySemester(
            @PathVariable Long semesterId
    ) {
        return ResponseEntity.ok(
                semesterSubjectService
                        .getSemesterSubjectsBySemester(semesterId)
        );
    }
}