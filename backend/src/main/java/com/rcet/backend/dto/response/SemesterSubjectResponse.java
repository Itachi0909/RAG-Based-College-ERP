package com.rcet.backend.dto.response;

public class SemesterSubjectResponse {

    private Long id;

    private Long semesterId;

    private Integer semesterNumber;

    private Long subjectId;

    private String subjectCode;

    private String subjectName;

    public SemesterSubjectResponse() {
    }

    public SemesterSubjectResponse(Long id,
                                   Long semesterId,
                                   Integer semesterNumber,
                                   Long subjectId,
                                   String subjectCode,
                                   String subjectName) {

        this.id = id;
        this.semesterId = semesterId;
        this.semesterNumber = semesterNumber;
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
    }

    public Long getId() {
        return id;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public Integer getSemesterNumber() {
        return semesterNumber;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public void setSemesterNumber(Integer semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}