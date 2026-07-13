package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.AttendanceBulkRequest;
import com.rcet.backend.dto.request.AttendanceItemRequest;
import com.rcet.backend.dto.response.AttendanceBulkResponse;
import com.rcet.backend.dto.response.AttendanceResponse;
import com.rcet.backend.dto.response.StudentAttendanceViewResponse;
import com.rcet.backend.entity.Attendance;
import com.rcet.backend.entity.AttendanceSession;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.Student;
import com.rcet.backend.enums.AttendanceSessionStatus;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.mapper.AttendanceMapper;
import com.rcet.backend.repository.AttendanceRepository;
import com.rcet.backend.repository.AttendanceSessionRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.service.AttendanceService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceSessionRepository attendanceSessionRepository;
    private final StudentRepository studentRepository;

    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            AttendanceSessionRepository attendanceSessionRepository,
            StudentRepository studentRepository) {

        this.attendanceRepository = attendanceRepository;
        this.attendanceSessionRepository = attendanceSessionRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public AttendanceBulkResponse markAttendance(
            AttendanceBulkRequest request) {

        AttendanceSession session =
                attendanceSessionRepository
                        .findById(request.getAttendanceSessionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attendance Session not found"));

        if (session.getStatus() == AttendanceSessionStatus.CANCELLED) {
            throw new RuntimeException("Attendance session is cancelled.");
        }

        List<AttendanceResponse> responseList = new ArrayList<>();

        int present = 0;
        int absent = 0;
        int late = 0;
        int leave = 0;

        for (AttendanceItemRequest item : request.getAttendanceList()) {

            Student student =
                    studentRepository.findById(item.getStudentId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Student not found"));

            if (attendanceRepository.existsByAttendanceSessionAndStudent(
                    session,
                    student)) {

                throw new RuntimeException(
                        "Attendance already marked for student : "
                                + student.getEnrollmentNumber());
            }

            Attendance attendance = new Attendance();

            attendance.setAttendanceSession(session);
            attendance.setStudent(student);
            attendance.setAttendanceStatus(item.getAttendanceStatus());
            attendance.setRemark(item.getRemark());

            attendanceRepository.save(attendance);

            responseList.add(
                    AttendanceMapper.toAttendanceResponse(attendance));

            switch (item.getAttendanceStatus()) {

                case PRESENT -> present++;

                case ABSENT -> absent++;

                case LATE -> late++;

                case LEAVE -> leave++;
            }
        }

        AttendanceBulkResponse response =
                new AttendanceBulkResponse();

        response.setAttendanceSessionId(session.getId());
        response.setAttendance(responseList);

        response.setTotalStudents(responseList.size());

        response.setPresentCount(present);

        response.setAbsentCount(absent);

        response.setLateCount(late);

        response.setLeaveCount(leave);

        return response;
    }
    @Override
    public AttendanceBulkResponse updateAttendance(
            AttendanceBulkRequest request) {

        AttendanceSession session =
                attendanceSessionRepository
                        .findById(request.getAttendanceSessionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attendance Session not found"));

        List<AttendanceResponse> responseList =
                new ArrayList<>();

        int present = 0;
        int absent = 0;
        int late = 0;
        int leave = 0;

        for (AttendanceItemRequest item : request.getAttendanceList()) {

            Student student =
                    studentRepository.findById(item.getStudentId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Student not found"));

            Attendance attendance =
                    attendanceRepository
                            .findByAttendanceSessionAndStudent(
                                    session,
                                    student)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Attendance not found"));

            attendance.setAttendanceStatus(
                    item.getAttendanceStatus());

            attendance.setRemark(
                    item.getRemark());

            attendanceRepository.save(attendance);

            responseList.add(
                    AttendanceMapper.toAttendanceResponse(attendance));

            switch (item.getAttendanceStatus()) {

                case PRESENT -> present++;

                case ABSENT -> absent++;

                case LATE -> late++;

                case LEAVE -> leave++;
            }
        }

        AttendanceBulkResponse response =
                new AttendanceBulkResponse();

        response.setAttendanceSessionId(session.getId());

        response.setAttendance(responseList);

        response.setTotalStudents(responseList.size());

        response.setPresentCount(present);

        response.setAbsentCount(absent);

        response.setLateCount(late);

        response.setLeaveCount(leave);

        return response;
    }
    @Override
    public AttendanceResponse getAttendanceById(
            Long attendanceId) {

        Attendance attendance =
                attendanceRepository
                        .findById(attendanceId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attendance not found"));

        return AttendanceMapper
                .toAttendanceResponse(attendance);
    }

    @Override
    public List<AttendanceResponse> getAttendanceBySession(
            Long attendanceSessionId) {

        AttendanceSession session =
                attendanceSessionRepository
                        .findById(attendanceSessionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attendance Session not found"));

        return attendanceRepository
                .findByAttendanceSession(session)
                .stream()
                .map(AttendanceMapper::toAttendanceResponse)
                .toList();
    }

    @Override
    public List<AttendanceResponse> getAttendanceByStudent(
            Long studentId) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student not found"));

        return attendanceRepository
                .findByStudent(student)
                .stream()
                .map(AttendanceMapper::toAttendanceResponse)
                .toList();
    }
    @Override
    public List<StudentAttendanceViewResponse>
    getStudentsForAttendance(
            Long attendanceSessionId) {

        AttendanceSession session =
                attendanceSessionRepository
                        .findById(attendanceSessionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attendance Session not found"));

        Semester semester =
                session.getTeacherSubject()
                        .getSemesterSubject()
                        .getSemester();

        List<Student> students =
                studentRepository.findBySemester(semester);

        return students.stream()
                .map(AttendanceMapper::toStudentAttendanceViewResponse)
                .toList();
    }

    @Override
    public void deleteAttendance(Long attendanceId) {

        Attendance attendance =
                attendanceRepository
                        .findById(attendanceId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attendance not found"));

        attendanceRepository.delete(attendance);
    }
}