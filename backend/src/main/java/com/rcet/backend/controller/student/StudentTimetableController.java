package com.rcet.backend.controller.student;

import com.rcet.backend.dto.response.TimetableResponse;
import com.rcet.backend.entity.Student;
import com.rcet.backend.security.service.AuthenticatedUserService;
import com.rcet.backend.service.TimetableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/timetable")
public class StudentTimetableController {

    private final TimetableService timetableService;
    private final AuthenticatedUserService authenticatedUserService;

    public StudentTimetableController(
            TimetableService timetableService,
            AuthenticatedUserService authenticatedUserService
    ) {
        this.timetableService = timetableService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping
    public ResponseEntity<List<TimetableResponse>> getMyTimetable() {
        Student student = authenticatedUserService.getCurrentStudent();

        Long semesterId = student.getSemester().getId();

        List<TimetableResponse> timetable =
                timetableService.getTimetableBySemester(semesterId);

        return ResponseEntity.ok(timetable);
    }
}