package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.SubjectRequest;
import com.rcet.backend.dto.response.SubjectResponse;
import com.rcet.backend.entity.Subject;
import com.rcet.backend.repository.SubjectRepository;
import com.rcet.backend.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SubjectResponse createSubject(SubjectRequest request) {

        if (subjectRepository.existsBySubjectCode(request.getSubjectCode())) {
            throw new RuntimeException("Subject code already exists.");
        }

        Subject subject = new Subject();

        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());
        subject.setDescription(request.getDescription());
        subject.setCredits(request.getCredits());

        Subject savedSubject = subjectRepository.save(subject);

        return mapToResponse(savedSubject);
    }

    @Override
    public List<SubjectResponse> getAllSubjects() {

        return subjectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectResponse getSubjectById(Long id) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found."));

        return mapToResponse(subject);
    }

    @Override
    public SubjectResponse updateSubject(Long id, SubjectRequest request) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found."));

        if (!subject.getSubjectCode().equals(request.getSubjectCode())
                && subjectRepository.existsBySubjectCode(request.getSubjectCode())) {

            throw new RuntimeException("Subject code already exists.");
        }

        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());
        subject.setDescription(request.getDescription());
        subject.setCredits(request.getCredits());

        Subject updatedSubject = subjectRepository.save(subject);

        return mapToResponse(updatedSubject);
    }

    @Override
    public void deleteSubject(Long id) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found."));

        subjectRepository.delete(subject);
    }

    private SubjectResponse mapToResponse(Subject subject) {

        return new SubjectResponse(
                subject.getId(),
                subject.getSubjectCode(),
                subject.getSubjectName(),
                subject.getDescription(),
                subject.getCredits()
        );
    }
}