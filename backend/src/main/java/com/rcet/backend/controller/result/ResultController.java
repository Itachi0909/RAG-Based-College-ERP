package com.rcet.backend.controller.result;

import com.rcet.backend.dto.response.ResultResponse;
import com.rcet.backend.service.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/student/{studentId}/semester/{semesterId}")
    public ResponseEntity<ResultResponse> generateResult(
            @PathVariable Long studentId,
            @PathVariable Long semesterId) {

        return ResponseEntity.ok(
                resultService.generateResult(studentId, semesterId)
        );
    }
}