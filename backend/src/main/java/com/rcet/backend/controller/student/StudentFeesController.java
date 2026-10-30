package com.rcet.backend.controller.student;

import com.rcet.backend.dto.response.FeeResponse;
import com.rcet.backend.service.FeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/fees")
public class StudentFeesController {

    private final FeeService feeService;

    public StudentFeesController(FeeService feeService) {
        this.feeService = feeService;
    }

    @GetMapping
    public ResponseEntity<List<FeeResponse>> getMyFees() {
        return ResponseEntity.ok(feeService.getStudentFees());
    }
}