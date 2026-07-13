package com.rcet.backend.controller.student;

import com.rcet.backend.dto.response.AttendanceResponse;
import com.rcet.backend.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/attendance")
public class StudentAttendanceController {

    private final AttendanceService attendanceService;

    public StudentAttendanceController(
            AttendanceService attendanceService) {

        this.attendanceService = attendanceService;
    }

    /**
     * Get attendance of a student
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByStudent(studentId)
        );
    }
}