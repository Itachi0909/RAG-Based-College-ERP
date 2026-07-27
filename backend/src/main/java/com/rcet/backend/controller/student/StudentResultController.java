package com.rcet.backend.controller.student;

import com.rcet.backend.dto.response.StudentResultResponse;
import com.rcet.backend.service.StudentResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student/results")
public class StudentResultController {

    private final StudentResultService studentResultService;

    public StudentResultController(
            StudentResultService studentResultService) {

        this.studentResultService = studentResultService;
    }

    @GetMapping
    public ResponseEntity<StudentResultResponse> getMyResult() {

        return ResponseEntity.ok(
                studentResultService.getMyResult());
    }
}