package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.AttendanceRequest;
import com.rcet.backend.dto.response.AttendanceResponse;
import com.rcet.backend.entity.Attendance;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.TeacherSubject;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.AttendanceRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.repository.TeacherSubjectRepository;
import com.rcet.backend.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;

    public AttendanceServiceImpl(
            AttendanceRepository attendanceRepository,
            StudentRepository studentRepository,
            TeacherSubjectRepository teacherSubjectRepository) {

        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
    }
    @Override
    public AttendanceResponse createAttendance(AttendanceRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found."));

        TeacherSubject teacherSubject = teacherSubjectRepository
                .findById(request.getTeacherSubjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher Subject not found."));

        if (attendanceRepository
                .existsByStudentAndTeacherSubjectAndAttendanceDateAndLectureNumber(
                        student,
                        teacherSubject,
                        request.getAttendanceDate(),
                        request.getLectureNumber())) {

            throw new DuplicateResourceException(
                    "Attendance already marked for this lecture.");
        }

        Attendance attendance = Attendance.builder()
                .student(student)
                .teacherSubject(teacherSubject)
                .attendanceDate(request.getAttendanceDate())
                .lectureNumber(request.getLectureNumber())
                .status(request.getStatus())
                .remarks(request.getRemarks())
                .build();

        Attendance savedAttendance = attendanceRepository.save(attendance);

        return mapToResponse(savedAttendance);
    }
    @Override
    public List<AttendanceResponse> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public AttendanceResponse getAttendanceById(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance not found."));

        return mapToResponse(attendance);
    }
    @Override
    public void deleteAttendance(Long id) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance not found."));

        attendanceRepository.delete(attendance);
    }
    @Override
    public AttendanceResponse updateAttendance(
            Long id,
            AttendanceRequest request) {

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Attendance not found."));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found."));

        TeacherSubject teacherSubject = teacherSubjectRepository
                .findById(request.getTeacherSubjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher Subject not found."));

        Attendance existing = attendanceRepository.findAll()
                .stream()
                .filter(a ->
                        a.getStudent().getId().equals(student.getId())
                                && a.getTeacherSubject().getId().equals(teacherSubject.getId())
                                && a.getAttendanceDate().equals(request.getAttendanceDate())
                                && a.getLectureNumber().equals(request.getLectureNumber())
                                && !a.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            throw new DuplicateResourceException(
                    "Attendance already marked for this lecture.");
        }

        attendance.setStudent(student);
        attendance.setTeacherSubject(teacherSubject);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setLectureNumber(request.getLectureNumber());
        attendance.setStatus(request.getStatus());
        attendance.setRemarks(request.getRemarks());

        Attendance updatedAttendance = attendanceRepository.save(attendance);

        return mapToResponse(updatedAttendance);
    }
    private AttendanceResponse mapToResponse(Attendance attendance) {

        return AttendanceResponse.builder()
                .id(attendance.getId())

                // Student
                .studentId(attendance.getStudent().getId())
                .enrollmentNumber(attendance.getStudent().getEnrollmentNumber())
                .studentName(
                        attendance.getStudent().getFirstName()
                                + " "
                                + attendance.getStudent().getLastName())

                // Teacher
                .teacherId(attendance.getTeacherSubject().getTeacher().getId())
                .teacherName(
                        attendance.getTeacherSubject().getTeacher().getFirstName()
                                + " "
                                + attendance.getTeacherSubject().getTeacher().getLastName())

                // Subject
                .subjectId(attendance.getTeacherSubject().getSubject().getId())
                .subjectCode(attendance.getTeacherSubject().getSubject().getSubjectCode())
                .subjectName(attendance.getTeacherSubject().getSubject().getSubjectName())

                // Attendance
                .attendanceDate(attendance.getAttendanceDate())
                .lectureNumber(attendance.getLectureNumber())
                .status(attendance.getStatus())
                .remarks(attendance.getRemarks())

                .build();
    }
}