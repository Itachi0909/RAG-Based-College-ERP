package com.rcet.backend.service;

import com.rcet.backend.dto.request.NoticeRequest;
import com.rcet.backend.dto.response.NoticeResponse;

import java.util.List;

public interface NoticeService {

    NoticeResponse createNotice(
            NoticeRequest request);

    List<NoticeResponse> getAllNotices();

    NoticeResponse getNoticeById(
            Long id);

    NoticeResponse updateNotice(
            Long id,
            NoticeRequest request);

    void deleteNotice(
            Long id);

    List<NoticeResponse> getStudentNotices(
            Long studentId);

    List<NoticeResponse> getTeacherNotices(
            Long teacherId);
}