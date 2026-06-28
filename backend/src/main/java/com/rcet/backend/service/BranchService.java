package com.rcet.backend.service;

import com.rcet.backend.dto.request.BranchRequest;
import com.rcet.backend.dto.response.BranchResponse;

import java.util.List;

public interface BranchService {

    BranchResponse createBranch(BranchRequest request);

    List<BranchResponse> getAllBranches();

    BranchResponse getBranchById(Long id);

    BranchResponse updateBranch(Long id, BranchRequest request);

    void deleteBranch(Long id);

}