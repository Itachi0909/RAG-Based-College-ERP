package com.rcet.backend.controller.student;

import com.rcet.backend.dto.response.NoticeResponse;
import com.rcet.backend.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/notices")
public class StudentNoticesController {

    private final NoticeService noticeService;

    public StudentNoticesController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponse>> getMyNotices() {
        return ResponseEntity.ok(
                noticeService.getStudentNotices()
        );
    }
}