package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.InitializeResultRequest;
import com.rcet.backend.dto.response.InitializeResultResponse;
import com.rcet.backend.entity.Branch;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.SemesterResult;
import com.rcet.backend.entity.Student;
import com.rcet.backend.enums.ResultStatus;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.BranchRepository;
import com.rcet.backend.repository.SemesterRepository;
import com.rcet.backend.repository.SemesterResultRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.service.AdminResultService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AdminResultServiceImpl
        implements AdminResultService {

    private final SemesterRepository semesterRepository;
    private final BranchRepository branchRepository;
    private final StudentRepository studentRepository;
    private final SemesterResultRepository semesterResultRepository;

    public AdminResultServiceImpl(
            SemesterRepository semesterRepository,
            BranchRepository branchRepository,
            StudentRepository studentRepository,
            SemesterResultRepository semesterResultRepository) {

        this.semesterRepository = semesterRepository;
        this.branchRepository = branchRepository;
        this.studentRepository = studentRepository;
        this.semesterResultRepository = semesterResultRepository;
    }

    @Override
    public InitializeResultResponse initializeSemesterResults(
            InitializeResultRequest request) {

        Semester semester = semesterRepository.findById(
                        request.getSemesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester not found"));

        Branch branch = branchRepository.findById(
                        request.getBranchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Branch not found"));

        List<Student> students =
                studentRepository.findByBranchAndSemester(
                        branch,
                        semester);

        List<SemesterResult> newResults =
                new ArrayList<>();

        int created = 0;
        int alreadyExists = 0;

        for (Student student : students) {

            boolean exists =
                    semesterResultRepository
                            .findByStudentAndSemester(
                                    student,
                                    semester)
                            .isPresent();

            if (exists) {
                alreadyExists++;
                continue;
            }

            SemesterResult result =
                    new SemesterResult();

            result.setStudent(student);
            result.setSemester(semester);

            result.setStatus(ResultStatus.DRAFT);

            result.setSgpa(BigDecimal.ZERO);

            result.setPercentage(BigDecimal.ZERO);

            newResults.add(result);

            created++;
        }

        semesterResultRepository.saveAll(newResults);

        InitializeResultResponse response =
                new InitializeResultResponse();

        response.setSemesterId(semester.getId());

        response.setBranchId(branch.getId());

        response.setTotalStudents(
                students.size());

        response.setCreatedResults(
                created);

        response.setAlreadyInitialized(
                alreadyExists);

        response.setMessage(
                "Semester Results Initialized Successfully.");

        return response;
    }

    @Override
    public void publishResults() {

        List<SemesterResult> semesterResults =
                semesterResultRepository.findByStatus(
                        ResultStatus.SUBMITTED);

        if (semesterResults.isEmpty()) {

            throw new IllegalArgumentException(
                    "No submitted results available for publishing.");
        }

        for (SemesterResult semesterResult : semesterResults) {

            semesterResult.setStatus(
                    ResultStatus.PUBLISHED);

            semesterResult.setPublishedAt(
                    java.time.LocalDateTime.now());
        }

        semesterResultRepository.saveAll(
                semesterResults);
    }
}