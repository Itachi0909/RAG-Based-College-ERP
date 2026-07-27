package com.rcet.backend.controller.branch;

import com.rcet.backend.dto.request.BranchRequest;
import com.rcet.backend.dto.response.BranchResponse;
import com.rcet.backend.service.BranchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping
    public ResponseEntity<BranchResponse> createBranch(
            @Valid @RequestBody BranchRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(branchService.createBranch(request));
    }

    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAllBranches() {

        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponse> getBranchById(
            @PathVariable Long id) {

        return ResponseEntity.ok(branchService.getBranchById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponse> updateBranch(
            @PathVariable Long id,
            @Valid @RequestBody BranchRequest request) {

        return ResponseEntity.ok(
                branchService.updateBranch(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBranch(
            @PathVariable Long id) {

        branchService.deleteBranch(id);

        return ResponseEntity.ok("Branch deleted successfully.");
    }
}