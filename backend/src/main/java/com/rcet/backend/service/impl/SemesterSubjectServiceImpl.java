package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.SemesterSubjectRequest;
import com.rcet.backend.dto.response.SemesterSubjectResponse;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.SemesterSubject;
import com.rcet.backend.entity.Subject;
import com.rcet.backend.repository.SemesterRepository;
import com.rcet.backend.repository.SemesterSubjectRepository;
import com.rcet.backend.repository.SubjectRepository;
import com.rcet.backend.service.SemesterSubjectService;
import org.springframework.stereotype.Service;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterSubjectServiceImpl implements SemesterSubjectService {

    private final SemesterSubjectRepository semesterSubjectRepository;
    private final SemesterRepository semesterRepository;
    private final SubjectRepository subjectRepository;

    public SemesterSubjectServiceImpl(
            SemesterSubjectRepository semesterSubjectRepository,
            SemesterRepository semesterRepository,
            SubjectRepository subjectRepository) {

        this.semesterSubjectRepository = semesterSubjectRepository;
        this.semesterRepository = semesterRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SemesterSubjectResponse createSemesterSubject(
            SemesterSubjectRequest request) {

        Semester semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Semester not found."));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subject not found."));

        if (semesterSubjectRepository.existsBySemesterAndSubject(semester, subject)) {
            throw new DuplicateResourceException(
                    "Subject is already assigned to this semester.");
        }

        SemesterSubject semesterSubject = new SemesterSubject();
        semesterSubject.setSemester(semester);
        semesterSubject.setSubject(subject);

        SemesterSubject saved = semesterSubjectRepository.save(semesterSubject);

        return mapToResponse(saved);
    }

    @Override
    public List<SemesterSubjectResponse> getAllSemesterSubjects() {

        return semesterSubjectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SemesterSubjectResponse getSemesterSubjectById(Long id) {

        SemesterSubject semesterSubject = semesterSubjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester Subject mapping not found."));

        return mapToResponse(semesterSubject);
    }

    @Override
    public SemesterSubjectResponse updateSemesterSubject(
            Long id,
            SemesterSubjectRequest request) {

        SemesterSubject semesterSubject = semesterSubjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester Subject mapping not found."));

        Semester semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Semester not found."));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Subject not found."));

        if (!semesterSubject.getSemester().equals(semester)
                || !semesterSubject.getSubject().equals(subject)) {

            if (semesterSubjectRepository.existsBySemesterAndSubject(semester, subject)) {
                throw new DuplicateResourceException(
                        "Subject is already assigned to this semester.");
            }
        }

        semesterSubject.setSemester(semester);
        semesterSubject.setSubject(subject);

        SemesterSubject updated = semesterSubjectRepository.save(semesterSubject);

        return mapToResponse(updated);
    }

    @Override
    public void deleteSemesterSubject(Long id) {

        SemesterSubject semesterSubject = semesterSubjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester Subject mapping not found."));

        semesterSubjectRepository.delete(semesterSubject);
    }

    private SemesterSubjectResponse mapToResponse(SemesterSubject semesterSubject) {

        return new SemesterSubjectResponse(
                semesterSubject.getId(),
                semesterSubject.getSemester().getId(),
                semesterSubject.getSemester().getSemesterNumber(),
                semesterSubject.getSubject().getId(),
                semesterSubject.getSubject().getSubjectCode(),
                semesterSubject.getSubject().getSubjectName()
        );
    }
}