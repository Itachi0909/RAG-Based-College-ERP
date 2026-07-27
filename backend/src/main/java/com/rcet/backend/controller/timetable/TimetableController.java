package com.rcet.backend.controller.timetable;

import com.rcet.backend.dto.request.TimetableRequest;
import com.rcet.backend.dto.response.TimetableResponse;
import com.rcet.backend.service.TimetableService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/timetable")
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(
            TimetableService timetableService) {

        this.timetableService = timetableService;
    }

    @PostMapping
    public ResponseEntity<TimetableResponse> createTimetable(
            @Valid @RequestBody TimetableRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(timetableService.createTimetable(request));
    }

    @GetMapping
    public ResponseEntity<List<TimetableResponse>> getAllTimetables() {

        return ResponseEntity.ok(
                timetableService.getAllTimetables());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimetableResponse> getTimetableById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                timetableService.getTimetableById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimetableResponse> updateTimetable(
            @PathVariable Long id,
            @Valid @RequestBody TimetableRequest request) {

        return ResponseEntity.ok(
                timetableService.updateTimetable(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTimetable(
            @PathVariable Long id) {

        timetableService.deleteTimetable(id);

        return ResponseEntity.ok(
                "Timetable deleted successfully.");
    }

    @GetMapping("/student")
    public ResponseEntity<List<TimetableResponse>>
    getStudentTimetable() {

        return ResponseEntity.ok(
                timetableService.getCurrentStudentTimetable());
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<TimetableResponse>>
    getTeacherTimetable() {

        return ResponseEntity.ok(
                timetableService.getCurrentTeacherTimetable());
    }

    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<List<TimetableResponse>>
    getSemesterTimetable(
            @PathVariable Long semesterId) {

        return ResponseEntity.ok(
                timetableService.getTimetableBySemester(semesterId));
    }
}