package com.rcet.backend.dto.response;

import com.rcet.backend.enums.AttendanceStatus;

public class AttendanceResponse {

    private Long id;

    private Long attendanceSessionId;

    private Long studentId;

    private String enrollmentNumber;

    private String rollNumber;

    private String studentName;

    private AttendanceStatus attendanceStatus;

    private String remark;

    public AttendanceResponse() {
    }

    public AttendanceResponse(
            Long id,
            Long attendanceSessionId,
            Long studentId,
            String enrollmentNumber,
            String rollNumber,
            String studentName,
            AttendanceStatus attendanceStatus,
            String remark) {

        this.id = id;
        this.attendanceSessionId = attendanceSessionId;
        this.studentId = studentId;
        this.enrollmentNumber = enrollmentNumber;
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.attendanceStatus = attendanceStatus;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public Long getAttendanceSessionId() {
        return attendanceSessionId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAttendanceSessionId(Long attendanceSessionId) {
        this.attendanceSessionId = attendanceSessionId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}