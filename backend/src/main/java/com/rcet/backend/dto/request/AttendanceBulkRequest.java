package com.rcet.backend.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AttendanceBulkRequest {

    @NotNull(message = "Attendance Session ID is required")
    private Long attendanceSessionId;

    @Valid
    @NotEmpty(message = "Attendance list cannot be empty")
    private List<AttendanceItemRequest> attendanceList;

    public AttendanceBulkRequest() {
    }

    public Long getAttendanceSessionId() {
        return attendanceSessionId;
    }

    public void setAttendanceSessionId(Long attendanceSessionId) {
        this.attendanceSessionId = attendanceSessionId;
    }

    public List<AttendanceItemRequest> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(
            List<AttendanceItemRequest> attendanceList) {

        this.attendanceList = attendanceList;
    }
}