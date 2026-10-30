package com.rcet.backend.controller.teachersubject;

import com.rcet.backend.dto.request.TeacherSubjectRequest;
import com.rcet.backend.dto.response.TeacherSubjectResponse;
import com.rcet.backend.service.TeacherSubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/teacher-subjects")
public class TeacherSubjectController {

    private final TeacherSubjectService teacherSubjectService;

    public TeacherSubjectController(
            TeacherSubjectService teacherSubjectService) {

        this.teacherSubjectService = teacherSubjectService;
    }

    // ==========================================
    // Create Teacher Subject Assignment
    // ==========================================

    @PostMapping
    public ResponseEntity<TeacherSubjectResponse> createTeacherSubject(
            @Valid @RequestBody TeacherSubjectRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teacherSubjectService.createTeacherSubject(request));
    }

    // ==========================================
    // Get All Teacher Subject Assignments
    // ==========================================

    @GetMapping
    public ResponseEntity<List<TeacherSubjectResponse>>
    getAllTeacherSubjects() {

        return ResponseEntity.ok(
                teacherSubjectService.getAllTeacherSubjects()
        );
    }

    // ==========================================
    // Get Teacher Subject Assignment By ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<TeacherSubjectResponse>
    getTeacherSubjectById(@PathVariable Long id) {

        return ResponseEntity.ok(
                teacherSubjectService.getTeacherSubjectById(id)
        );
    }

    // ==========================================
    // Update Teacher Subject Assignment
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<TeacherSubjectResponse>
    updateTeacherSubject(
            @PathVariable Long id,
            @Valid @RequestBody TeacherSubjectRequest request) {

        return ResponseEntity.ok(
                teacherSubjectService.updateTeacherSubject(id, request)
        );
    }

    // ==========================================
    // Delete Teacher Subject Assignment
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacherSubject(
            @PathVariable Long id) {

        teacherSubjectService.deleteTeacherSubject(id);

        return ResponseEntity.ok(
                "Teacher Subject assignment deleted successfully.");
    }
}