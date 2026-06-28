package com.rcet.backend.controller.semester;

import com.rcet.backend.dto.request.SemesterRequest;
import com.rcet.backend.dto.response.SemesterResponse;
import com.rcet.backend.service.SemesterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @PostMapping
    public ResponseEntity<SemesterResponse> createSemester(
            @Valid @RequestBody SemesterRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(semesterService.createSemester(request));
    }

    @GetMapping
    public ResponseEntity<List<SemesterResponse>> getAllSemesters() {

        return ResponseEntity.ok(semesterService.getAllSemesters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemesterResponse> getSemesterById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                semesterService.getSemesterById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemesterResponse> updateSemester(
            @PathVariable Long id,
            @Valid @RequestBody SemesterRequest request) {

        return ResponseEntity.ok(
                semesterService.updateSemester(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSemester(
            @PathVariable Long id) {

        semesterService.deleteSemester(id);

        return ResponseEntity.ok("Semester deleted successfully.");
    }
}