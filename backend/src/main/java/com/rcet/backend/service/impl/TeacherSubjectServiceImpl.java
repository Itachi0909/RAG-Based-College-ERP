package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.TeacherSubjectRequest;
import com.rcet.backend.dto.response.TeacherSubjectResponse;
import com.rcet.backend.entity.Subject;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.TeacherSubject;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.SubjectRepository;
import com.rcet.backend.repository.TeacherRepository;
import com.rcet.backend.repository.TeacherSubjectRepository;
import com.rcet.backend.service.TeacherSubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherSubjectServiceImpl implements TeacherSubjectService {

    private final TeacherSubjectRepository teacherSubjectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public TeacherSubjectServiceImpl(
            TeacherSubjectRepository teacherSubjectRepository,
            TeacherRepository teacherRepository,
            SubjectRepository subjectRepository) {

        this.teacherSubjectRepository = teacherSubjectRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }
    @Override
    public TeacherSubjectResponse createTeacherSubject(TeacherSubjectRequest request) {

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher not found."));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subject not found."));

        if (teacherSubjectRepository.existsByTeacherAndSubject(teacher, subject)) {
            throw new DuplicateResourceException(
                    "Teacher is already assigned to this subject.");
        }

        TeacherSubject teacherSubject = TeacherSubject.builder()
                .teacher(teacher)
                .subject(subject)
                .build();

        TeacherSubject saved = teacherSubjectRepository.save(teacherSubject);

        return mapToResponse(saved);
    }
    @Override
    public List<TeacherSubjectResponse> getAllTeacherSubjects() {

        return teacherSubjectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public TeacherSubjectResponse getTeacherSubjectById(Long id) {

        TeacherSubject teacherSubject = teacherSubjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher Subject mapping not found."));

        return mapToResponse(teacherSubject);
    }
    @Override
    public void deleteTeacherSubject(Long id) {

        TeacherSubject teacherSubject = teacherSubjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher Subject mapping not found."));

        teacherSubjectRepository.delete(teacherSubject);
    }
    @Override
    public TeacherSubjectResponse updateTeacherSubject(
            Long id,
            TeacherSubjectRequest request) {

        TeacherSubject teacherSubject = teacherSubjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher Subject mapping not found."));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher not found."));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subject not found."));

        TeacherSubject existing = teacherSubjectRepository
                .findAll()
                .stream()
                .filter(ts -> ts.getTeacher().getId().equals(teacher.getId())
                        && ts.getSubject().getId().equals(subject.getId())
                        && !ts.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            throw new DuplicateResourceException(
                    "Teacher is already assigned to this subject.");
        }

        teacherSubject.setTeacher(teacher);
        teacherSubject.setSubject(subject);

        TeacherSubject updated =
                teacherSubjectRepository.save(teacherSubject);

        return mapToResponse(updated);
    }
    private TeacherSubjectResponse mapToResponse(
            TeacherSubject teacherSubject) {

        return TeacherSubjectResponse.builder()
                .id(teacherSubject.getId())

                .teacherId(teacherSubject.getTeacher().getId())
                .employeeId(teacherSubject.getTeacher().getEmployeeId())
                .teacherName(
                        teacherSubject.getTeacher().getFirstName()
                                + " "
                                + teacherSubject.getTeacher().getLastName())

                .subjectId(teacherSubject.getSubject().getId())
                .subjectCode(teacherSubject.getSubject().getSubjectCode())
                .subjectName(teacherSubject.getSubject().getSubjectName())

                .build();
    }
}