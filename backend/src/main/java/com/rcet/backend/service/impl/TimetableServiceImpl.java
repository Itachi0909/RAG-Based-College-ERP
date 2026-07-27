package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.TimetableRequest;
import com.rcet.backend.dto.response.TimetableResponse;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.TeacherSubject;
import com.rcet.backend.entity.Timetable;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.repository.TeacherRepository;
import com.rcet.backend.repository.TeacherSubjectRepository;
import com.rcet.backend.repository.TimetableRepository;
import com.rcet.backend.service.TimetableService;
import org.springframework.stereotype.Service;
import com.rcet.backend.security.service.AuthenticatedUserService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository timetableRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public TimetableServiceImpl(
            TimetableRepository timetableRepository,
            TeacherSubjectRepository teacherSubjectRepository,
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            AuthenticatedUserService authenticatedUserService) {

        this.timetableRepository = timetableRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public TimetableResponse createTimetable(
            TimetableRequest request) {

        TeacherSubject teacherSubject =
                teacherSubjectRepository.findById(
                                request.getTeacherSubjectId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher Subject not found."));


        if (timetableRepository
                .existsByTeacherSubjectAndDayOfWeekAndStartTime(
                        teacherSubject,
                        request.getDayOfWeek(),
                        request.getStartTime())) {

            throw new DuplicateResourceException(
                    "Timetable already exists.");
        }

        Timetable timetable = Timetable.builder()

                .teacherSubject(teacherSubject)

                .dayOfWeek(request.getDayOfWeek())

                .startTime(request.getStartTime())

                .endTime(request.getEndTime())

                .roomNumber(request.getRoomNumber())

                .lectureType(request.getLectureType())

                .build();

        Timetable saved =
                timetableRepository.save(timetable);

        return mapToResponse(saved);
    }

    @Override
    public List<TimetableResponse> getAllTimetables() {

        return timetableRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TimetableResponse getTimetableById(Long id) {

        Timetable timetable =
                timetableRepository.findByIdAndActiveTrue(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Timetable not found."));

        return mapToResponse(timetable);
    }

    @Override
    public TimetableResponse updateTimetable(
            Long id,
            TimetableRequest request) {

        Timetable timetable =
                timetableRepository.findByIdAndActiveTrue(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Timetable not found."));

        TeacherSubject teacherSubject =
                teacherSubjectRepository.findById(
                                request.getTeacherSubjectId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher Subject not found."));



        timetableRepository.findByTeacherSubjectAndActiveTrue(teacherSubject)
                .stream()
                .filter(existing ->
                        existing.getDayOfWeek() == request.getDayOfWeek()
                                && existing.getStartTime().equals(request.getStartTime())
                                && !existing.getId().equals(id))
                .findFirst()
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Timetable already exists.");
                });

        timetable.setTeacherSubject(teacherSubject);
        timetable.setDayOfWeek(request.getDayOfWeek());
        timetable.setStartTime(request.getStartTime());
        timetable.setEndTime(request.getEndTime());
        timetable.setRoomNumber(request.getRoomNumber());
        timetable.setLectureType(request.getLectureType());

        Timetable updated =
                timetableRepository.save(timetable);

        return mapToResponse(updated);
    }

    @Override
    public void deleteTimetable(Long id) {

        Timetable timetable =
                timetableRepository.findByIdAndActiveTrue(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Timetable not found."));



        timetable.setActive(false);

        timetableRepository.save(timetable);
    }

    @Override
    public List<TimetableResponse> getTimetableByStudent(
            Long studentId) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student not found."));

        return timetableRepository.findByTeacherSubject_SemesterSubject_SemesterAndActiveTrue(
                        student.getSemester())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TimetableResponse> getCurrentStudentTimetable() {

        Student student =
                authenticatedUserService.getCurrentStudent();

        return timetableRepository
                .findByTeacherSubject_SemesterSubject_SemesterAndActiveTrue(
                        student.getSemester())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TimetableResponse> getTimetableByTeacher(
            Long teacherId) {

        Teacher teacher =
                teacherRepository.findById(teacherId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher not found."));

        return timetableRepository.findByTeacherSubject_TeacherAndActiveTrue(
                        teacher)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TimetableResponse> getCurrentTeacherTimetable() {

        Teacher teacher =
                authenticatedUserService.getCurrentTeacher();

        return timetableRepository
                .findByTeacherSubject_TeacherAndActiveTrue(
                        teacher)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<TimetableResponse> getTimetableBySemester(
            Long semesterId) {

        return timetableRepository.findByTeacherSubject_SemesterSubject_Semester_IdAndActiveTrue(
                        semesterId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TimetableResponse mapToResponse(
            Timetable timetable) {

        return TimetableResponse.builder()

                .id(timetable.getId())

                // ==========================
                // Teacher
                // ==========================

                .teacherId(
                        timetable.getTeacherSubject()
                                .getTeacher()
                                .getId())

                .teacherName(
                        timetable.getTeacherSubject()
                                .getTeacher()
                                .getFirstName()
                                + " "
                                + timetable.getTeacherSubject()
                                .getTeacher()
                                .getLastName())

                // ==========================
                // Semester
                // ==========================

                .semesterId(
                        timetable.getTeacherSubject()
                                .getSemesterSubject()
                                .getSemester()
                                .getId())

                .semesterNumber(
                        timetable.getTeacherSubject()
                                .getSemesterSubject()
                                .getSemester()
                                .getSemesterNumber())

                // ==========================
                // Branch
                // ==========================

                .branchId(
                        timetable.getTeacherSubject()
                                .getSemesterSubject()
                                .getSemester()
                                .getBranch()
                                .getId())

                .branchName(
                        timetable.getTeacherSubject()
                                .getSemesterSubject()
                                .getSemester()
                                .getBranch()
                                .getName())

                // ==========================
                // Subject
                // ==========================

                .subjectId(
                        timetable.getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getId())

                .subjectCode(
                        timetable.getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getSubjectCode())

                .subjectName(
                        timetable.getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getSubjectName())

                // ==========================
                // Timetable
                // ==========================

                .dayOfWeek(timetable.getDayOfWeek())

                .startTime(timetable.getStartTime())

                .endTime(timetable.getEndTime())

                .roomNumber(timetable.getRoomNumber())

                .lectureType(timetable.getLectureType())

                .active(timetable.getActive())

                .build();
    }
}
