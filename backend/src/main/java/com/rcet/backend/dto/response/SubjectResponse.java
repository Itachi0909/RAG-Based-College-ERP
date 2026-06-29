package com.rcet.backend.dto.response;

public class SubjectResponse {

    private Long id;

    private String subjectCode;

    private String subjectName;

    private String description;

    private Integer credits;

    public SubjectResponse() {
    }

    public SubjectResponse(Long id,
                           String subjectCode,
                           String subjectName,
                           String description,
                           Integer credits) {

        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.description = description;
        this.credits = credits;
    }

    public Long getId() {
        return id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}