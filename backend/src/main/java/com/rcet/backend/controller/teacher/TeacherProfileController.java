package com.rcet.backend.controller.teacher;

import com.rcet.backend.dto.response.TeacherResponse;
import com.rcet.backend.mapper.TeacherMapper;
import com.rcet.backend.security.service.AuthenticatedUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
public class TeacherProfileController {

    private final AuthenticatedUserService authenticatedUserService;

    public TeacherProfileController(
            AuthenticatedUserService authenticatedUserService
    ) {
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping("/profile")
    public ResponseEntity<TeacherResponse> getMyProfile() {

        TeacherResponse response = TeacherMapper.toResponse(
                authenticatedUserService.getCurrentTeacher()
        );

        return ResponseEntity.ok(response);
    }
}