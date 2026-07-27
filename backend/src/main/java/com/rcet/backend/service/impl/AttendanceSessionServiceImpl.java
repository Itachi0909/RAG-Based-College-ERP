package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.AttendanceSessionRequest;
import com.rcet.backend.dto.response.AttendanceSessionResponse;
import com.rcet.backend.entity.AttendanceSession;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.TeacherSubject;
import com.rcet.backend.enums.AttendanceSessionStatus;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.mapper.AttendanceMapper;
import com.rcet.backend.repository.AttendanceSessionRepository;
import com.rcet.backend.repository.TeacherSubjectRepository;
import com.rcet.backend.security.service.AuthenticatedUserService;
import com.rcet.backend.service.AttendanceSessionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AttendanceSessionServiceImpl
        implements AttendanceSessionService {

    private final AttendanceSessionRepository attendanceSessionRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public AttendanceSessionServiceImpl(
            AttendanceSessionRepository attendanceSessionRepository,
            TeacherSubjectRepository teacherSubjectRepository,
            AuthenticatedUserService authenticatedUserService) {

        this.attendanceSessionRepository = attendanceSessionRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public AttendanceSessionResponse createAttendanceSession(
            AttendanceSessionRequest request) {

        Teacher teacher =
                authenticatedUserService.getCurrentTeacher();

        TeacherSubject teacherSubject =
                teacherSubjectRepository
                        .findById(request.getTeacherSubjectId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher Subject not found"));

        if (!teacherSubject.getTeacher().getId()
                .equals(teacher.getId())) {

            throw new RuntimeException(
                    "You are not allowed to create attendance for this subject.");
        }

        AttendanceSession session =
                new AttendanceSession();

        session.setTeacherSubject(teacherSubject);
        session.setAttendanceDate(request.getAttendanceDate());
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        session.setTopic(request.getTopic());
        session.setRemarks(request.getRemarks());
        session.setStatus(
                AttendanceSessionStatus.SCHEDULED);

        attendanceSessionRepository.save(session);

        return AttendanceMapper
                .toAttendanceSessionResponse(session);
    }

    @Override
    public AttendanceSessionResponse getAttendanceSessionById(
            Long id) {

        AttendanceSession session =
                attendanceSessionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attendance Session not found"));

        return AttendanceMapper
                .toAttendanceSessionResponse(session);
    }

    @Override
    public List<AttendanceSessionResponse> getAllAttendanceSessions() {

        return attendanceSessionRepository
                .findAll()
                .stream()
                .map(AttendanceMapper::toAttendanceSessionResponse)
                .toList();
    }

    @Override
    public List<AttendanceSessionResponse> getAttendanceSessionsByTeacher(
            Long teacherId) {

        return attendanceSessionRepository
                .findByTeacherSubjectTeacherId(teacherId)
                .stream()
                .map(AttendanceMapper::toAttendanceSessionResponse)
                .toList();
    }

    @Override
    public AttendanceSessionResponse updateAttendanceSession(
            Long id,
            AttendanceSessionRequest request) {

        AttendanceSession session =
                attendanceSessionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attendance Session not found"));

        session.setAttendanceDate(request.getAttendanceDate());
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());
        session.setTopic(request.getTopic());
        session.setRemarks(request.getRemarks());

        attendanceSessionRepository.save(session);

        return AttendanceMapper
                .toAttendanceSessionResponse(session);
    }

    @Override
    public void deleteAttendanceSession(Long id) {

        AttendanceSession session =
                attendanceSessionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attendance Session not found"));

        attendanceSessionRepository.delete(session);
    }
}