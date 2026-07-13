package com.rcet.backend.dto.response;

public class SemesterSubjectResponse {

    private Long id;

    private Long semesterId;

    private Integer semesterNumber;

    private Long subjectId;

    private String subjectCode;

    private String subjectName;

    private Integer internalMarks;

    private Integer externalMarks;

    private Integer passingMarks;

    private Integer totalMarks;

    private boolean active;

    public SemesterSubjectResponse() {
    }

    public SemesterSubjectResponse(
            Long id,
            Long semesterId,
            Integer semesterNumber,
            Long subjectId,
            String subjectCode,
            String subjectName,
            Integer internalMarks,
            Integer externalMarks,
            Integer passingMarks,
            Integer totalMarks,
            boolean active) {

        this.id = id;
        this.semesterId = semesterId;
        this.semesterNumber = semesterNumber;
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.internalMarks = internalMarks;
        this.externalMarks = externalMarks;
        this.passingMarks = passingMarks;
        this.totalMarks = totalMarks;
        this.active = active;
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

    public Integer getInternalMarks() {
        return internalMarks;
    }

    public Integer getExternalMarks() {
        return externalMarks;
    }

    public Integer getPassingMarks() {
        return passingMarks;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public boolean isActive() {
        return active;
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

    public void setInternalMarks(Integer internalMarks) {
        this.internalMarks = internalMarks;
    }

    public void setExternalMarks(Integer externalMarks) {
        this.externalMarks = externalMarks;
    }

    public void setPassingMarks(Integer passingMarks) {
        this.passingMarks = passingMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}