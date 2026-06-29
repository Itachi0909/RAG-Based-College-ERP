package com.rcet.backend.controller.marks;

import com.rcet.backend.dto.request.MarksRequest;
import com.rcet.backend.dto.response.MarksResponse;
import com.rcet.backend.service.MarksService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marks")
public class MarksController {

    private final MarksService marksService;

    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }

    @PostMapping
    public ResponseEntity<MarksResponse> createMarks(
            @Valid @RequestBody MarksRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(marksService.createMarks(request));
    }

    @GetMapping
    public ResponseEntity<List<MarksResponse>> getAllMarks() {

        return ResponseEntity.ok(marksService.getAllMarks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarksResponse> getMarksById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                marksService.getMarksById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarksResponse> updateMarks(
            @PathVariable Long id,
            @Valid @RequestBody MarksRequest request) {

        return ResponseEntity.ok(
                marksService.updateMarks(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMarks(
            @PathVariable Long id) {

        marksService.deleteMarks(id);

        return ResponseEntity.ok("Marks deleted successfully.");
    }
}