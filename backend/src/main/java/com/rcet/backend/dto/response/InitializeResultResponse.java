package com.rcet.backend.dto.response;

public class InitializeResultResponse {

    private Long semesterId;

    private Long branchId;

    private Integer totalStudents;

    private Integer createdResults;

    private Integer alreadyInitialized;

    private String message;

    public InitializeResultResponse() {
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Integer getCreatedResults() {
        return createdResults;
    }

    public void setCreatedResults(Integer createdResults) {
        this.createdResults = createdResults;
    }

    public Integer getAlreadyInitialized() {
        return alreadyInitialized;
    }

    public void setAlreadyInitialized(Integer alreadyInitialized) {
        this.alreadyInitialized = alreadyInitialized;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}