package com.rcet.backend.controller.transcript;

import com.rcet.backend.dto.response.TranscriptResponse;
import com.rcet.backend.service.TranscriptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transcript")
public class TranscriptController {

    private final TranscriptService transcriptService;

    public TranscriptController(
            TranscriptService transcriptService) {

        this.transcriptService = transcriptService;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<TranscriptResponse> generateTranscript(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                transcriptService.generateTranscript(studentId));
    }
}