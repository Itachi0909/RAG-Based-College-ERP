package com.rcet.backend.controller.attendance;

import com.rcet.backend.dto.request.AttendanceRequest;
import com.rcet.backend.dto.response.AttendanceResponse;
import com.rcet.backend.service.AttendanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<AttendanceResponse> createAttendance(
            @Valid @RequestBody AttendanceRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(attendanceService.createAttendance(request));
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> getAllAttendance() {

        return ResponseEntity.ok(attendanceService.getAllAttendance());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponse> getAttendanceById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponse> updateAttendance(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceRequest request) {

        return ResponseEntity.ok(
                attendanceService.updateAttendance(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendance(
            @PathVariable Long id) {

        attendanceService.deleteAttendance(id);

        return ResponseEntity.ok("Attendance deleted successfully.");
    }
}