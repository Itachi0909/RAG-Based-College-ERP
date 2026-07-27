package com.rcet.backend.dto.request;

import jakarta.validation.constraints.NotNull;

public class InitializeResultRequest {

    @NotNull
    private Long semesterId;

    @NotNull
    private Long branchId;

    public InitializeResultRequest() {
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