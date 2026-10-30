package com.rcet.backend.controller.teacher;

import com.rcet.backend.dto.response.NoticeResponse;
import com.rcet.backend.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/notices")
public class TeacherNoticesController {

    private final NoticeService noticeService;

    public TeacherNoticesController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponse>> getMyNotices() {
        return ResponseEntity.ok(
                noticeService.getTeacherNotices()
        );
    }
}