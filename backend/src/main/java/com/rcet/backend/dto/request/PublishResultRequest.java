package com.rcet.backend.dto.request;

import jakarta.validation.constraints.NotNull;

public class PublishResultRequest {

    @NotNull
    private Long semesterId;

    @NotNull
    private Long branchId;

    public PublishResultRequest() {
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
}