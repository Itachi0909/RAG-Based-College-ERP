package com.rcet.backend.dto.response;

import com.rcet.backend.enums.Grade;

public class StudentMarksResponse {

    private Long studentId;

    private String enrollmentNumber;

    private String rollNumber;

    private String studentName;

    // Existing Marks
    private Integer internalMarks;

    private Integer theoryMarks;

    private Grade grade;

    // Marking Scheme
    private Integer maxInternalMarks;

    private Integer maxTheoryMarks;

    private Integer passingMarks;

    private Integer totalMarks;

    public StudentMarksResponse() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(Integer internalMarks) {
        this.internalMarks = internalMarks;
    }

    public Integer getTheoryMarks() {
        return theoryMarks;
    }

    public void setTheoryMarks(Integer theoryMarks) {
        this.theoryMarks = theoryMarks;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Integer getMaxInternalMarks() {
        return maxInternalMarks;
    }

    public void setMaxInternalMarks(Integer maxInternalMarks) {
        this.maxInternalMarks = maxInternalMarks;
    }

    public Integer getMaxTheoryMarks() {
        return maxTheoryMarks;
    }

    public void setMaxTheoryMarks(Integer maxTheoryMarks) {
        this.maxTheoryMarks = maxTheoryMarks;
    }

    public Integer getPassingMarks() {
        return passingMarks;
    }

    public void setPassingMarks(Integer passingMarks) {
        this.passingMarks = passingMarks;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }
}