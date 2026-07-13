package com.rcet.backend.controller.admin;

import com.rcet.backend.dto.response.AttendanceResponse;
import com.rcet.backend.dto.response.AttendanceSessionResponse;
import com.rcet.backend.service.AttendanceService;
import com.rcet.backend.service.AttendanceSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/attendance")
public class AdminAttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceSessionService attendanceSessionService;

    public AdminAttendanceController(
            AttendanceService attendanceService,
            AttendanceSessionService attendanceSessionService) {

        this.attendanceService = attendanceService;
        this.attendanceSessionService = attendanceSessionService;
    }

    /**
     * Get all attendance sessions
     */
    @GetMapping("/sessions")
    public ResponseEntity<List<AttendanceSessionResponse>> getAllSessions() {

        return ResponseEntity.ok(
                attendanceSessionService.getAllAttendanceSessions()
        );
    }

    /**
     * Get attendance session by id
     */
    @GetMapping("/session/{id}")
    public ResponseEntity<AttendanceSessionResponse> getSession(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attendanceSessionService.getAttendanceSessionById(id)
        );
    }

    /**
     * Get attendance of a session
     */
    @GetMapping("/session/{sessionId}/records")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceBySession(
            @PathVariable Long sessionId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceBySession(sessionId)
        );
    }

    /**
     * Delete attendance record
     */
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<String> deleteAttendance(
            @PathVariable Long attendanceId) {

        attendanceService.deleteAttendance(attendanceId);

        return ResponseEntity.ok(
                "Attendance deleted successfully."
        );
    }
}