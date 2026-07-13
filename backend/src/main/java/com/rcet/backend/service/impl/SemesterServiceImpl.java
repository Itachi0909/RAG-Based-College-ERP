package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.SemesterRequest;
import com.rcet.backend.dto.response.SemesterResponse;
import com.rcet.backend.entity.Branch;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.BranchRepository;
import com.rcet.backend.repository.SemesterRepository;
import com.rcet.backend.service.SemesterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;
    private final BranchRepository branchRepository;

    public SemesterServiceImpl(SemesterRepository semesterRepository,
                               BranchRepository branchRepository) {

        this.semesterRepository = semesterRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public SemesterResponse createSemester(SemesterRequest request) {

        Branch branch = getBranch(request.getBranchId());

        if (semesterRepository.existsBySemesterNumberAndBranch(
                request.getSemesterNumber(), branch)) {

            throw new DuplicateResourceException(
                    "Semester already exists in this branch.");
        }

        Semester semester = new Semester();
        semester.setSemesterNumber(request.getSemesterNumber());
        semester.setDescription(request.getDescription());
        semester.setBranch(branch);

        Semester savedSemester = semesterRepository.save(semester);

        return mapToResponse(savedSemester);
    }

    @Override
    public List<SemesterResponse> getAllSemesters() {

        return semesterRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SemesterResponse getSemesterById(Long id) {

        Semester semester = getSemester(id);

        return mapToResponse(semester);
    }

    @Override
    public SemesterResponse updateSemester(Long id,
                                           SemesterRequest request) {

        Semester semester = getSemester(id);

        Branch branch = getBranch(request.getBranchId());

        semesterRepository.findBySemesterNumberAndBranch(
                        request.getSemesterNumber(),
                        branch)
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {

                        throw new DuplicateResourceException(
                                "Semester already exists in this branch.");
                    }
                });

        semester.setSemesterNumber(request.getSemesterNumber());
        semester.setDescription(request.getDescription());
        semester.setBranch(branch);

        Semester updatedSemester = semesterRepository.save(semester);

        return mapToResponse(updatedSemester);
    }

    @Override
    public void deleteSemester(Long id) {

        Semester semester = getSemester(id);

        semesterRepository.delete(semester);
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

    private Branch getBranch(Long id) {

        return branchRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Branch not found."));
    }

    private SemesterResponse mapToResponse(Semester semester) {

        return new SemesterResponse(
                semester.getId(),
                semester.getSemesterNumber(),
                semester.getDescription(),
                semester.getBranch().getId(),
                semester.getBranch().getName()
        );
    }
}