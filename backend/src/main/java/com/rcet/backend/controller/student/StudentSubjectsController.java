package com.rcet.backend.controller.student;

import com.rcet.backend.dto.response.SemesterSubjectResponse;
import com.rcet.backend.entity.Student;
import com.rcet.backend.security.service.AuthenticatedUserService;
import com.rcet.backend.service.SemesterSubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/subjects")
public class StudentSubjectsController {

    private final SemesterSubjectService semesterSubjectService;
    private final AuthenticatedUserService authenticatedUserService;

    public StudentSubjectsController(
            SemesterSubjectService semesterSubjectService,
            AuthenticatedUserService authenticatedUserService
    ) {
        this.semesterSubjectService = semesterSubjectService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping
    public ResponseEntity<List<SemesterSubjectResponse>> getMySubjects() {

        Student student =
                authenticatedUserService.getCurrentStudent();

        Long semesterId =
                student.getSemester().getId();

        List<SemesterSubjectResponse> subjects =
                semesterSubjectService
                        .getSemesterSubjectsBySemester(semesterId);

        return ResponseEntity.ok(subjects);
    }
}