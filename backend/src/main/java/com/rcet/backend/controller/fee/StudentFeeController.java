package com.rcet.backend.controller.fee;

import com.rcet.backend.dto.response.FeeResponse;
import com.rcet.backend.service.FeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/fees")
public class StudentFeeController {

    private final FeeService feeService;

    public StudentFeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @GetMapping
    public ResponseEntity<List<FeeResponse>> getStudentFees() {

        return ResponseEntity.ok(
                feeService.getStudentFees());
    }
}