package com.rcet.backend.controller.student;

import com.rcet.backend.dto.response.AttendanceResponse;
import com.rcet.backend.security.service.AuthenticatedUserService;
import com.rcet.backend.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/attendance")
public class StudentAttendanceController {

    private final AttendanceService attendanceService;
    private final AuthenticatedUserService authenticatedUserService;

    public StudentAttendanceController(
            AttendanceService attendanceService,
            AuthenticatedUserService authenticatedUserService) {

        this.attendanceService = attendanceService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @GetMapping
    public ResponseEntity<List<AttendanceResponse>> getMyAttendance() {

        Long studentId = authenticatedUserService
                .getCurrentStudent()
                .getId();

        return ResponseEntity.ok(
                attendanceService.getAttendanceByStudent(studentId)
        );
    }
}