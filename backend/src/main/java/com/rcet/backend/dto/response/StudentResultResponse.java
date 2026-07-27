package com.rcet.backend.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class StudentResultResponse {

    private String enrollmentNumber;

    private String studentName;

    private Integer semester;

    private String branch;

    private BigDecimal sgpa;

    private BigDecimal percentage;

    private String status;

    private List<StudentSubjectResultResponse> subjects;

    public StudentResultResponse() {
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public BigDecimal getSgpa() {
        return sgpa;
    }

    public void setSgpa(BigDecimal sgpa) {
        this.sgpa = sgpa;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StudentSubjectResultResponse> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<StudentSubjectResultResponse> subjects) {
        this.subjects = subjects;
    }
}