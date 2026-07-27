package com.rcet.backend.controller.admin;

import com.rcet.backend.dto.request.InitializeResultRequest;
import com.rcet.backend.dto.response.InitializeResultResponse;
import com.rcet.backend.service.AdminResultService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/results")
public class AdminResultController {

    private final AdminResultService adminResultService;

    public AdminResultController(
            AdminResultService adminResultService) {

        this.adminResultService = adminResultService;
    }

    @PostMapping("/initialize")
    public ResponseEntity<InitializeResultResponse>
    initializeSemesterResults(

            @Valid
            @RequestBody
            InitializeResultRequest request) {

        return ResponseEntity.ok(
                adminResultService
                        .initializeSemesterResults(request)
        );
    }

    @PostMapping("/publish")
    public ResponseEntity<String>
    publishResults() {

        adminResultService.publishResults();

        return ResponseEntity.ok(
                "Results published successfully.");
    }

}