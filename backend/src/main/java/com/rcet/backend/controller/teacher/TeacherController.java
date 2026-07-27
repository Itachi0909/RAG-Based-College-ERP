package com.rcet.backend.controller.teacher;

import com.rcet.backend.dto.request.TeacherRequest;
import com.rcet.backend.dto.response.TeacherResponse;
import com.rcet.backend.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // ======================================
    // Create Teacher
    // ======================================

    @PostMapping
    public ResponseEntity<TeacherResponse> createTeacher(
            @Valid @RequestBody TeacherRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teacherService.createTeacher(request));
    }

    // ======================================
    // Get All Teachers
    // ======================================

    @GetMapping
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {

        return ResponseEntity.ok(
                teacherService.getAllTeachers()
        );
    }

    // ======================================
    // Get Teacher By ID
    // ======================================

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getTeacherById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                teacherService.getTeacherById(id)
        );
    }

    // ======================================
    // Update Teacher
    // ======================================

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponse> updateTeacher(
            @PathVariable Long id,
            @Valid @RequestBody TeacherRequest request) {

        return ResponseEntity.ok(
                teacherService.updateTeacher(id, request)
        );
    }

    // ======================================
    // Delete Teacher
    // ======================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(
            @PathVariable Long id) {

        teacherService.deleteTeacher(id);

        return ResponseEntity.ok(
                "Teacher deleted successfully."
        );
    }
}