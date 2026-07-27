package com.rcet.backend.dto.response;

public class MarksBulkResponse {

    private Long teacherSubjectId;

    private Integer totalStudents;

    private Integer savedRecords;

    private String message;

    public MarksBulkResponse() {
    }

    public Long getTeacherSubjectId() {
        return teacherSubjectId;
    }

    public void setTeacherSubjectId(Long teacherSubjectId) {
        this.teacherSubjectId = teacherSubjectId;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Integer getSavedRecords() {
        return savedRecords;
    }

    public void setSavedRecords(Integer savedRecords) {
        this.savedRecords = savedRecords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}