package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.SemesterSubjectRequest;
import com.rcet.backend.dto.response.SemesterSubjectResponse;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.SemesterSubject;
import com.rcet.backend.entity.Subject;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.SemesterRepository;
import com.rcet.backend.repository.SemesterSubjectRepository;
import com.rcet.backend.repository.SubjectRepository;
import com.rcet.backend.service.SemesterSubjectService;
import org.springframework.stereotype.Service;

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

        Semester semester = getSemester(request.getSemesterId());

        Subject subject = getSubject(request.getSubjectId());

        if (semesterSubjectRepository.existsBySemesterAndSubject(
                semester, subject)) {

            throw new DuplicateResourceException(
                    "Subject is already assigned to this semester.");
        }

        SemesterSubject semesterSubject = new SemesterSubject();

        semesterSubject.setSemester(semester);
        semesterSubject.setSubject(subject);
        semesterSubject.setInternalMarks(request.getInternalMarks());
        semesterSubject.setExternalMarks(request.getExternalMarks());
        semesterSubject.setPassingMarks(request.getPassingMarks());

        int totalMarks =
                request.getInternalMarks() + request.getExternalMarks();

        if (request.getPassingMarks() > totalMarks) {
            throw new IllegalArgumentException(
                    "Passing marks cannot exceed total marks.");
        }

        semesterSubject.setTotalMarks(totalMarks);

        semesterSubject.setActive(true);

        SemesterSubject saved =
                semesterSubjectRepository.save(semesterSubject);

        return mapToResponse(saved);
    }

    @Override
    public List<SemesterSubjectResponse> getAllSemesterSubjects() {

        return semesterSubjectRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SemesterSubjectResponse getSemesterSubjectById(Long id) {

        return mapToResponse(getSemesterSubject(id));
    }

    @Override
    public SemesterSubjectResponse updateSemesterSubject(
            Long id,
            SemesterSubjectRequest request) {

        SemesterSubject semesterSubject = getSemesterSubject(id);

        Semester semester = getSemester(request.getSemesterId());

        Subject subject = getSubject(request.getSubjectId());

        semesterSubjectRepository.findBySemesterAndSubject(
                        semester,
                        subject)
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {

                        throw new DuplicateResourceException(
                                "Subject is already assigned to this semester.");
                    }
                });

        semesterSubject.setSemester(semester);
        semesterSubject.setSubject(subject);
        semesterSubject.setInternalMarks(request.getInternalMarks());
        semesterSubject.setExternalMarks(request.getExternalMarks());
        semesterSubject.setPassingMarks(request.getPassingMarks());

        int totalMarks =
                request.getInternalMarks() + request.getExternalMarks();

        if (request.getPassingMarks() > totalMarks) {
            throw new IllegalArgumentException(
                    "Passing marks cannot exceed total marks.");
        }

        semesterSubject.setTotalMarks(totalMarks);

        SemesterSubject updated =
                semesterSubjectRepository.save(semesterSubject);

        return mapToResponse(updated);
    }

    @Override
    public List<SemesterSubjectResponse> getSemesterSubjectsBySemester(
            Long semesterId
    ) {
        return semesterSubjectRepository
                .findBySemesterIdAndActiveTrue(semesterId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSemesterSubject(Long id) {

        SemesterSubject semesterSubject = getSemesterSubject(id);

        semesterSubject.setActive(false);

        semesterSubjectRepository.save(semesterSubject);
    }

    // ==========================
    // Helper Methods
    // ==========================

    private Semester getSemester(Long id) {

        return semesterRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester not found."));
    }

    private Subject getSubject(Long id) {

        return subjectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subject not found."));
    }

    private SemesterSubject getSemesterSubject(Long id) {

        return semesterSubjectRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester Subject mapping not found."));
    }

    private SemesterSubjectResponse mapToResponse(
            SemesterSubject semesterSubject) {

        return new SemesterSubjectResponse(

                semesterSubject.getId(),

                semesterSubject.getSemester().getId(),
                semesterSubject.getSemester().getSemesterNumber(),

                semesterSubject.getSubject().getId(),
                semesterSubject.getSubject().getSubjectCode(),
                semesterSubject.getSubject().getSubjectName(),

                semesterSubject.getInternalMarks(),
                semesterSubject.getExternalMarks(),
                semesterSubject.getPassingMarks(),
                semesterSubject.getTotalMarks(),

                semesterSubject.isActive()
        );
    }
}