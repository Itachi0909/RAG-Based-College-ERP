package com.rcet.backend.controller.student;

import com.rcet.backend.dto.response.StudentResponse;
import com.rcet.backend.mapper.StudentMapper;
import com.rcet.backend.security.service.AuthenticatedUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/student")
public class StudentProfileController {

    private final AuthenticatedUserService authenticatedUserService;

    public StudentProfileController(
            AuthenticatedUserService authenticatedUserService) {

        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("/profile")
    public ResponseEntity<StudentResponse> getMyProfile() {

        StudentResponse response = StudentMapper.toResponse(
                authenticatedUserService.getCurrentStudent()
        );

        return ResponseEntity.ok(response);
    }
}