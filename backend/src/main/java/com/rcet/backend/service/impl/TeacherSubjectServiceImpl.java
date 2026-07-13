package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.TeacherSubjectRequest;
import com.rcet.backend.dto.response.TeacherSubjectResponse;
import com.rcet.backend.entity.SemesterSubject;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.TeacherSubject;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.mapper.TeacherSubjectMapper;
import com.rcet.backend.repository.SemesterSubjectRepository;
import com.rcet.backend.repository.TeacherRepository;
import com.rcet.backend.repository.TeacherSubjectRepository;
import com.rcet.backend.service.TeacherSubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherSubjectServiceImpl
        implements TeacherSubjectService {

    private final TeacherSubjectRepository teacherSubjectRepository;
    private final TeacherRepository teacherRepository;
    private final SemesterSubjectRepository semesterSubjectRepository;

    public TeacherSubjectServiceImpl(
            TeacherSubjectRepository teacherSubjectRepository,
            TeacherRepository teacherRepository,
            SemesterSubjectRepository semesterSubjectRepository) {

        this.teacherSubjectRepository = teacherSubjectRepository;
        this.teacherRepository = teacherRepository;
        this.semesterSubjectRepository = semesterSubjectRepository;
    }

    // ==========================================
    // Create
    // ==========================================

    @Override
    public TeacherSubjectResponse createTeacherSubject(
            TeacherSubjectRequest request) {

        Teacher teacher = getTeacher(request.getTeacherId());

        SemesterSubject semesterSubject =
                getSemesterSubject(request.getSemesterSubjectId());

        if (teacherSubjectRepository.existsByTeacherAndSemesterSubject(
                teacher,
                semesterSubject)) {

            throw new DuplicateResourceException(
                    "Teacher is already assigned to this subject.");
        }

        TeacherSubject teacherSubject =
                new TeacherSubject();

        teacherSubject.setTeacher(teacher);
        teacherSubject.setSemesterSubject(semesterSubject);

        TeacherSubject saved =
                teacherSubjectRepository.save(teacherSubject);

        return TeacherSubjectMapper.toResponse(saved);
    }

    // ==========================================
    // Get By Id
    // ==========================================

    @Override
    public TeacherSubjectResponse getTeacherSubjectById(
            Long id) {

        return TeacherSubjectMapper.toResponse(
                getTeacherSubject(id));
    }

    // ==========================================
    // Get All
    // ==========================================

    @Override
    public List<TeacherSubjectResponse>
    getAllTeacherSubjects() {

        return teacherSubjectRepository.findAll()
                .stream()
                .map(TeacherSubjectMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ==========================================
    // Update
    // ==========================================

    @Override
    public TeacherSubjectResponse updateTeacherSubject(
            Long id,
            TeacherSubjectRequest request) {

        TeacherSubject teacherSubject =
                getTeacherSubject(id);

        Teacher teacher =
                getTeacher(request.getTeacherId());

        SemesterSubject semesterSubject =
                getSemesterSubject(request.getSemesterSubjectId());

        if ((!teacherSubject.getTeacher().equals(teacher))
                || (!teacherSubject.getSemesterSubject()
                .equals(semesterSubject))) {

            if (teacherSubjectRepository
                    .existsByTeacherAndSemesterSubject(
                            teacher,
                            semesterSubject)) {

                throw new DuplicateResourceException(
                        "Teacher is already assigned to this subject.");
            }
        }

        teacherSubject.setTeacher(teacher);
        teacherSubject.setSemesterSubject(semesterSubject);

        TeacherSubject updated =
                teacherSubjectRepository.save(teacherSubject);

        return TeacherSubjectMapper.toResponse(updated);
    }

    // ==========================================
    // Delete
    // ==========================================

    @Override
    public void deleteTeacherSubject(Long id) {

        TeacherSubject teacherSubject =
                getTeacherSubject(id);

        teacherSubjectRepository.delete(teacherSubject);
    }

    // ==========================================
    // Helper Methods
    // ==========================================

    private TeacherSubject getTeacherSubject(
            Long id) {

        return teacherSubjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher Subject assignment not found."));
    }

    private Teacher getTeacher(
            Long id) {

        return teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher not found."));
    }

    private SemesterSubject getSemesterSubject(
            Long id) {

        return semesterSubjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester Subject not found."));
    }
}