package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.MarksRequest;
import com.rcet.backend.dto.response.MarksResponse;
import com.rcet.backend.entity.Marks;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.TeacherSubject;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.MarksRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.repository.TeacherSubjectRepository;
import com.rcet.backend.service.MarksService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarksServiceImpl implements MarksService {

    private final MarksRepository marksRepository;
    private final StudentRepository studentRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;

    public MarksServiceImpl(
            MarksRepository marksRepository,
            StudentRepository studentRepository,
            TeacherSubjectRepository teacherSubjectRepository) {

        this.marksRepository = marksRepository;
        this.studentRepository = studentRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
    }
    @Override
    public MarksResponse createMarks(MarksRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found."));

        TeacherSubject teacherSubject = teacherSubjectRepository
                .findById(request.getTeacherSubjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher Subject not found."));

        if (marksRepository.existsByStudentAndTeacherSubjectAndExamType(
                student,
                teacherSubject,
                request.getExamType())) {

            throw new DuplicateResourceException(
                    "Marks already exist for this exam.");
        }

        if (request.getObtainedMarks() > request.getMaximumMarks()) {
            throw new IllegalArgumentException(
                    "Obtained marks cannot be greater than maximum marks.");
        }

        Marks marks = Marks.builder()
                .student(student)
                .teacherSubject(teacherSubject)
                .examType(request.getExamType())
                .maximumMarks(request.getMaximumMarks())
                .obtainedMarks(request.getObtainedMarks())
                .weightage(request.getWeightage())
                .remarks(request.getRemarks())
                .build();

        Marks savedMarks = marksRepository.save(marks);

        return mapToResponse(savedMarks);
    }
    @Override
    public List<MarksResponse> getAllMarks() {

        return marksRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public MarksResponse getMarksById(Long id) {

        Marks marks = marksRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Marks not found."));

        return mapToResponse(marks);
    }
    @Override
    public void deleteMarks(Long id) {

        Marks marks = marksRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Marks not found."));

        marksRepository.delete(marks);
    }
    @Override
    public MarksResponse updateMarks(Long id, MarksRequest request) {

        Marks marks = marksRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Marks not found."));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found."));

        TeacherSubject teacherSubject = teacherSubjectRepository
                .findById(request.getTeacherSubjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher Subject not found."));

        Marks existing = marksRepository.findAll()
                .stream()
                .filter(m ->
                        m.getStudent().getId().equals(student.getId())
                                && m.getTeacherSubject().getId().equals(teacherSubject.getId())
                                && m.getExamType().equals(request.getExamType())
                                && !m.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            throw new DuplicateResourceException(
                    "Marks already exist for this exam.");
        }

        if (request.getObtainedMarks() > request.getMaximumMarks()) {
            throw new IllegalArgumentException(
                    "Obtained marks cannot be greater than maximum marks.");
        }

        marks.setStudent(student);
        marks.setTeacherSubject(teacherSubject);
        marks.setExamType(request.getExamType());
        marks.setMaximumMarks(request.getMaximumMarks());
        marks.setObtainedMarks(request.getObtainedMarks());
        marks.setWeightage(request.getWeightage());
        marks.setRemarks(request.getRemarks());

        Marks updatedMarks = marksRepository.save(marks);

        return mapToResponse(updatedMarks);
    }
    private MarksResponse mapToResponse(Marks marks) {

        double percentage = (marks.getObtainedMarks() / marks.getMaximumMarks()) * 100;

        return MarksResponse.builder()
                .id(marks.getId())

                // Student
                .studentId(marks.getStudent().getId())
                .enrollmentNumber(marks.getStudent().getEnrollmentNumber())
                .studentName(
                        marks.getStudent().getFirstName()
                                + " "
                                + marks.getStudent().getLastName())

                // Teacher
                .teacherId(marks.getTeacherSubject().getTeacher().getId())
                .teacherName(
                        marks.getTeacherSubject().getTeacher().getFirstName()
                                + " "
                                + marks.getTeacherSubject().getTeacher().getLastName())

                // Subject
                .subjectId(marks.getTeacherSubject().getSubject().getId())
                .subjectCode(marks.getTeacherSubject().getSubject().getSubjectCode())
                .subjectName(marks.getTeacherSubject().getSubject().getSubjectName())

                // Marks
                .examType(marks.getExamType())
                .maximumMarks(marks.getMaximumMarks())
                .obtainedMarks(marks.getObtainedMarks())
                .weightage(marks.getWeightage())
                .percentage(percentage)
                .remarks(marks.getRemarks())

                .build();
    }

}