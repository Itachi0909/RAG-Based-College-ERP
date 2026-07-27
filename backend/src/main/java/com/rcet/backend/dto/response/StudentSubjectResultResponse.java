package com.rcet.backend.dto.response;

import com.rcet.backend.enums.Grade;

public class StudentSubjectResultResponse {

    private String subjectCode;

    private String subjectName;

    private Integer internalMarks;

    private Integer theoryMarks;

    private Grade grade;

    private Integer totalMarks;

    public StudentSubjectResultResponse() {
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

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }
}