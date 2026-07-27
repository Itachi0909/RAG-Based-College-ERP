package com.rcet.backend.controller.teacher;

import com.rcet.backend.dto.request.AttendanceBulkRequest;
import com.rcet.backend.dto.request.AttendanceSessionRequest;
import com.rcet.backend.dto.response.AttendanceBulkResponse;
import com.rcet.backend.dto.response.AttendanceResponse;
import com.rcet.backend.dto.response.AttendanceSessionResponse;
import com.rcet.backend.dto.response.StudentAttendanceViewResponse;
import com.rcet.backend.service.AttendanceService;
import com.rcet.backend.service.AttendanceSessionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.security.service.AuthenticatedUserService;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/attendance")
public class TeacherAttendanceController {

    private final AttendanceSessionService attendanceSessionService;
    private final AttendanceService attendanceService;
    private final AuthenticatedUserService authenticatedUserService;

    public TeacherAttendanceController(
            AttendanceSessionService attendanceSessionService,
            AttendanceService attendanceService,
            AuthenticatedUserService authenticatedUserService) {

        this.attendanceSessionService = attendanceSessionService;
        this.attendanceService = attendanceService;
        this.authenticatedUserService = authenticatedUserService;
    }

    // ==========================================
    // Attendance Session
    // ==========================================

    @PostMapping("/session")
    public ResponseEntity<AttendanceSessionResponse> createAttendanceSession(
            @Valid @RequestBody AttendanceSessionRequest request) {

        return ResponseEntity.ok(
                attendanceSessionService.createAttendanceSession(request)
        );
    }

    @GetMapping("/session/{id}")
    public ResponseEntity<AttendanceSessionResponse> getAttendanceSession(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attendanceSessionService.getAttendanceSessionById(id)
        );
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<AttendanceSessionResponse>> getAllSessions() {

        Teacher teacher =
                authenticatedUserService.getCurrentTeacher();

        return ResponseEntity.ok(
                attendanceSessionService
                        .getAttendanceSessionsByTeacher(
                                teacher.getId()
                        )
        );
    }
    @GetMapping("/session/{sessionId}/students")
    public ResponseEntity<List<StudentAttendanceViewResponse>>
    getStudentsForAttendance(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                attendanceService.getStudentsForAttendance(sessionId)
        );
    }

    // ==========================================
    // Attendance
    // ==========================================

    @PostMapping("/mark")
    public ResponseEntity<AttendanceBulkResponse> markAttendance(
            @Valid @RequestBody AttendanceBulkRequest request) {

        return ResponseEntity.ok(
                attendanceService.markAttendance(request)
        );
    }

    @GetMapping("/session/{sessionId}/attendance")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceBySession(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceBySession(sessionId)
        );
    }

    @PutMapping("/mark")
    public ResponseEntity<AttendanceBulkResponse> updateAttendance(
            @Valid @RequestBody AttendanceBulkRequest request) {

        return ResponseEntity.ok(
                attendanceService.updateAttendance(request)
        );
    }

    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<String> deleteAttendance(
            @PathVariable Long attendanceId) {

        attendanceService.deleteAttendance(attendanceId);

        return ResponseEntity.ok("Attendance deleted successfully.");
    }

}