package com.rcet.backend.controller.notice;

import com.rcet.backend.dto.request.NoticeRequest;
import com.rcet.backend.dto.response.NoticeResponse;
import com.rcet.backend.service.NoticeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(
            NoticeService noticeService) {

        this.noticeService = noticeService;
    }

    @PostMapping
    public ResponseEntity<NoticeResponse> createNotice(
            @Valid @RequestBody NoticeRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(noticeService.createNotice(request));
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponse>> getAllNotices() {

        return ResponseEntity.ok(
                noticeService.getAllNotices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> getNoticeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                noticeService.getNoticeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponse> updateNotice(
            @PathVariable Long id,
            @Valid @RequestBody NoticeRequest request) {

        return ResponseEntity.ok(
                noticeService.updateNotice(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotice(
            @PathVariable Long id) {

        noticeService.deleteNotice(id);

        return ResponseEntity.ok(
                "Notice deleted successfully.");
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<NoticeResponse>> getStudentNotices(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                noticeService.getStudentNotices(studentId));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<NoticeResponse>> getTeacherNotices(
            @PathVariable Long teacherId) {

        return ResponseEntity.ok(
                noticeService.getTeacherNotices(teacherId));
    }
}