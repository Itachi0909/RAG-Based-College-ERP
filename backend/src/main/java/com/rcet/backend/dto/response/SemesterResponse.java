package com.rcet.backend.dto.response;

public class SemesterResponse {

    private Long id;

    private Integer semesterNumber;

    private String description;

    private Long branchId;

    private String branchName;

    public SemesterResponse() {
    }

    public SemesterResponse(Long id,
                            Integer semesterNumber,
                            String description,
                            Long branchId,
                            String branchName) {

        this.id = id;
        this.semesterNumber = semesterNumber;
        this.description = description;
        this.branchId = branchId;
        this.branchName = branchName;
    }

    public Long getId() {
        return id;
    }

    public Integer getSemesterNumber() {
        return semesterNumber;
    }

    public String getDescription() {
        return description;
    }

    public Long getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSemesterNumber(Integer semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}