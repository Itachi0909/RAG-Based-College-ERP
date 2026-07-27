package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.NoticeRequest;
import com.rcet.backend.dto.response.NoticeResponse;
import com.rcet.backend.entity.*;
import com.rcet.backend.enums.Role;
import com.rcet.backend.exception.ForbiddenOperationException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.*;
import com.rcet.backend.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.rcet.backend.security.service.AuthenticatedUserService;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final BranchRepository branchRepository;
    private final SemesterRepository semesterRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public NoticeServiceImpl(
            NoticeRepository noticeRepository,
            BranchRepository branchRepository,
            SemesterRepository semesterRepository,
            AuthenticatedUserService authenticatedUserService) {

        this.noticeRepository = noticeRepository;
        this.branchRepository = branchRepository;
        this.semesterRepository = semesterRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public NoticeResponse createNotice(
            NoticeRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        if (user.getRole() != Role.ADMIN) {
            throw new ForbiddenOperationException(
                    "Only admin can create Notice.");
        }

        Branch branch = null;
        Semester semester = null;

        if (request.getBranchId() != null) {

            branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Branch not found."));
        }

        if (request.getSemesterId() != null) {

            semester = semesterRepository.findById(request.getSemesterId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Semester not found."));
        }
        if (branch != null && semester != null) {

            if (!semester.getBranch().getId().equals(branch.getId())) {

                throw new IllegalArgumentException(
                        "Selected semester does not belong to the selected branch.");
            }
        }

        Notice notice = Notice.builder()

                .title(request.getTitle())

                .description(request.getDescription())

                .priority(request.getPriority())

                .targetAudience(request.getTargetAudience())

                .branch(branch)

                .semester(semester)

                .build();

        Notice saved =
                noticeRepository.save(notice);

        return mapToResponse(saved);
    }

    @Override
    public List<NoticeResponse> getAllNotices() {

        return noticeRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeResponse getNoticeById(
            Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notice not found."));

        return mapToResponse(notice);
    }

    @Override
    public NoticeResponse updateNotice(
            Long id,
            NoticeRequest request) {

        User user = authenticatedUserService.getCurrentUser();

        if (user.getRole() != Role.ADMIN) {
            throw new ForbiddenOperationException(
                    "Only admin can update Notice");
        }

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notice not found."));

        Branch branch = null;
        Semester semester = null;

        if (request.getBranchId() != null) {

            branch = branchRepository.findById(request.getBranchId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Branch not found."));
        }

        if (request.getSemesterId() != null) {

            semester = semesterRepository.findById(request.getSemesterId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Semester not found."));
        }

        if (branch != null && semester != null) {

            if (!semester.getBranch().getId().equals(branch.getId())) {

                throw new IllegalArgumentException(
                        "Selected semester does not belong to the selected branch.");
            }
        }

        notice.setTitle(request.getTitle());
        notice.setDescription(request.getDescription());
        notice.setPriority(request.getPriority());
        notice.setTargetAudience(request.getTargetAudience());
        notice.setBranch(branch);
        notice.setSemester(semester);

        Notice updated = noticeRepository.save(notice);

        return mapToResponse(updated);
    }

    @Override
    public void deleteNotice(Long id) {

        User user = authenticatedUserService.getCurrentUser();

        if (user.getRole() != Role.ADMIN) {
            throw new ForbiddenOperationException(
                    "Only admin can delete Notice");
        }

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notice not found."));

        notice.setActive(false);

        noticeRepository.save(notice);
    }

    @Override
    public List<NoticeResponse> getStudentNotices() {

        Student student =
                authenticatedUserService.getCurrentStudent();

        return noticeRepository.findStudentNotices(
                        student.getSemester().getBranch().getId(),
                        student.getSemester().getId(),
                        TargetAudience.ALL,
                        TargetAudience.STUDENTS)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeResponse> getTeacherNotices() {

        Teacher teacher =
                authenticatedUserService.getCurrentTeacher();

        return noticeRepository.findTeacherNotices(
                        teacher.getBranch().getId(),
                        TargetAudience.ALL,
                        TargetAudience.TEACHERS)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private NoticeResponse mapToResponse(
            Notice notice) {

        NoticeResponse.NoticeResponseBuilder builder =
                NoticeResponse.builder()

                        .id(notice.getId())

                        .title(notice.getTitle())

                        .description(notice.getDescription())

                        .priority(notice.getPriority())

                        .targetAudience(notice.getTargetAudience())

                        .active(notice.getActive());

        if (notice.getBranch() != null) {

            builder.branchId(
                    notice.getBranch().getId());

            builder.branchName(
                    notice.getBranch().getName());
        }

        if (notice.getSemester() != null) {

            builder.semesterId(
                    notice.getSemester().getId());

            builder.semesterNumber(
                    notice.getSemester().getSemesterNumber());
        }

        return builder.build();
    }
}