package com.rcet.backend.dto.response;

import com.rcet.backend.enums.AttendanceSessionStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceSessionResponse {

    private Long id;

    private Long teacherSubjectId;

    private String teacherName;

    private String subjectCode;

    private String subjectName;

    private Integer semesterNumber;

    private LocalDate attendanceDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String topic;

    private String remarks;

    private AttendanceSessionStatus status;

    private boolean active;

    public AttendanceSessionResponse() {
    }

    public AttendanceSessionResponse(
            Long id,
            Long teacherSubjectId,
            String teacherName,
            String subjectCode,
            String subjectName,
            Integer semesterNumber,
            LocalDate attendanceDate,
            LocalTime startTime,
            LocalTime endTime,
            String topic,
            String remarks,
            AttendanceSessionStatus status,
            boolean active) {

        this.id = id;
        this.teacherSubjectId = teacherSubjectId;
        this.teacherName = teacherName;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.semesterNumber = semesterNumber;
        this.attendanceDate = attendanceDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.topic = topic;
        this.remarks = remarks;
        this.status = status;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Long getTeacherSubjectId() {
        return teacherSubjectId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Integer getSemesterNumber() {
        return semesterNumber;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getTopic() {
        return topic;
    }

    public String getRemarks() {
        return remarks;
    }

    public AttendanceSessionStatus getStatus() {
        return status;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeacherSubjectId(Long teacherSubjectId) {
        this.teacherSubjectId = teacherSubjectId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSemesterNumber(Integer semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setStatus(AttendanceSessionStatus status) {
        this.status = status;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}