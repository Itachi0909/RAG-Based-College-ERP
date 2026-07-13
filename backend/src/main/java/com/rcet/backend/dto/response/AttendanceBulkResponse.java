package com.rcet.backend.dto.response;

import java.util.List;

public class AttendanceBulkResponse {

    private Long attendanceSessionId;

    private int totalStudents;

    private int presentCount;

    private int absentCount;

    private int lateCount;

    private int leaveCount;

    private List<AttendanceResponse> attendance;

    public AttendanceBulkResponse() {
    }

    public Long getAttendanceSessionId() {
        return attendanceSessionId;
    }

    public void setAttendanceSessionId(Long attendanceSessionId) {
        this.attendanceSessionId = attendanceSessionId;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(int presentCount) {
        this.presentCount = presentCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public void setLateCount(int lateCount) {
        this.lateCount = lateCount;
    }

    public int getLeaveCount() {
        return leaveCount;
    }

    public void setLeaveCount(int leaveCount) {
        this.leaveCount = leaveCount;
    }

    public List<AttendanceResponse> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<AttendanceResponse> attendance) {
        this.attendance = attendance;
    }
}