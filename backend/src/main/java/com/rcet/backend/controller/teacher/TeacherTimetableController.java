package com.rcet.backend.controller.teacher;

import com.rcet.backend.dto.response.TimetableResponse;
import com.rcet.backend.service.TimetableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/timetable")
public class TeacherTimetableController {

    private final TimetableService timetableService;

    public TeacherTimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @GetMapping
    public ResponseEntity<List<TimetableResponse>> getMyTimetable() {
        return ResponseEntity.ok(
                timetableService.getCurrentTeacherTimetable()
        );
    }
}