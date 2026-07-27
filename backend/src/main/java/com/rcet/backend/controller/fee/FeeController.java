package com.rcet.backend.controller.fee;

import com.rcet.backend.dto.request.FeePaymentRequest;
import com.rcet.backend.dto.request.FeeRequest;
import com.rcet.backend.dto.response.FeeResponse;
import com.rcet.backend.service.FeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(
            FeeService feeService) {

        this.feeService = feeService;
    }

    @PostMapping
    public ResponseEntity<FeeResponse> createFee(
            @Valid @RequestBody FeeRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(feeService.createFee(request));
    }

    @GetMapping
    public ResponseEntity<List<FeeResponse>> getAllFees() {

        return ResponseEntity.ok(
                feeService.getAllFees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeResponse> getFeeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                feeService.getFeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeeResponse> updateFee(
            @PathVariable Long id,
            @Valid @RequestBody FeeRequest request) {

        return ResponseEntity.ok(
                feeService.updateFee(id, request));
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<FeeResponse> payFee(
            @PathVariable Long id,
            @Valid @RequestBody FeePaymentRequest request) {

        return ResponseEntity.ok(
                feeService.payFee(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFee(
            @PathVariable Long id) {

        feeService.deleteFee(id);

        return ResponseEntity.ok(
                "Fee deleted successfully.");
    }
}