package com.rcet.backend.controller.teacher;

import com.rcet.backend.dto.response.TeacherSubjectResponse;
import com.rcet.backend.service.TeacherSubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/subjects")
public class TeacherSubjectsController {

    private final TeacherSubjectService teacherSubjectService;

    public TeacherSubjectsController(
            TeacherSubjectService teacherSubjectService) {

        this.teacherSubjectService = teacherSubjectService;
    }

    @GetMapping
    public ResponseEntity<List<TeacherSubjectResponse>> getMySubjects() {

        return ResponseEntity.ok(
                teacherSubjectService.getMyTeacherSubjects()
        );
    }
}