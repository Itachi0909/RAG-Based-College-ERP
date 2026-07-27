package com.rcet.backend.dto.response;

public class TeacherSubjectResultResponse {

    private Long teacherSubjectId;

    private String subjectCode;

    private String subjectName;

    private Integer semester;

    private String branch;

    private Integer credits;

    private Integer studentsCount;

    private String status;

    public TeacherSubjectResultResponse() {
    }

    public Long getTeacherSubjectId() {
        return teacherSubjectId;
    }

    public void setTeacherSubjectId(Long teacherSubjectId) {
        this.teacherSubjectId = teacherSubjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}