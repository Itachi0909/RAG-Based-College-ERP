package com.rcet.backend.dto.response;

public class BranchResponse {

    private Long id;

    private String name;

    private String description;

    private Long departmentId;

    private String departmentName;

    public BranchResponse() {
    }

    public BranchResponse(Long id,
                          String name,
                          String description,
                          Long departmentId,
                          String departmentName) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}