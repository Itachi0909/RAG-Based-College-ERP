package com.rcet.backend.service.impl;

import com.rcet.backend.dto.response.ResultResponse;
import com.rcet.backend.dto.response.SemesterTranscriptResponse;
import com.rcet.backend.dto.response.TranscriptResponse;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.Student;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.SemesterRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.security.service.AuthenticatedUserService;
import com.rcet.backend.service.ResultService;
import com.rcet.backend.service.TranscriptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TranscriptServiceImpl
        implements TranscriptService {

    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final ResultService resultService;
    private final AuthenticatedUserService authenticatedUserService;

    public TranscriptServiceImpl(
            StudentRepository studentRepository,
            SemesterRepository semesterRepository,
            ResultService resultService,
            AuthenticatedUserService authenticatedUserService) {

        this.studentRepository = studentRepository;
        this.semesterRepository = semesterRepository;
        this.resultService = resultService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public TranscriptResponse generateTranscript() {

        Student student = authenticatedUserService.getCurrentStudent();

        List<Semester> semesters =
                semesterRepository
                        .findByBranchOrderBySemesterNumberAsc(
                                student.getBranch());

        List<SemesterTranscriptResponse> semesterResults =
                new ArrayList<>();

        double totalSgpa = 0;

        int completedSemesters = 0;

        int backlogs = 0;
        for (Semester semester : semesters) {

            try {

                ResultResponse result =
                        resultService.generateResult(
                                student.getId(),
                                semester.getId());

                semesterResults.add(
                        SemesterTranscriptResponse.builder()
                                .semesterId(result.getSemesterId())
                                .semesterNumber(result.getSemesterNumber())
                                .sgpa(result.getSgpa())
                                .grade(result.getGrade())
                                .passed(result.isPassed())
                                .build());

                totalSgpa += result.getSgpa();

                completedSemesters++;

                if (!result.isPassed()) {
                    backlogs++;
                }

            } catch (ResourceNotFoundException ex) {

                // No result for this semester.
                // Skip and continue with the next semester.

            }

        }

        double cgpa = completedSemesters == 0
                ? 0
                : totalSgpa / completedSemesters;

        return TranscriptResponse.builder()

                // ==========================
                // Student
                // ==========================

                .studentId(student.getId())

                .enrollmentNumber(student.getEnrollmentNumber())

                .studentName(
                        student.getFirstName()
                                + " "
                                + student.getLastName())

                .branchName(
                        student.getBranch().getName())

                // ==========================
                // Semester History
                // ==========================

                .semesters(semesterResults)

                // ==========================
                // Overall
                // ==========================

                .cgpa(
                        Math.round(cgpa * 100.0) / 100.0)

                .completedSemesters(completedSemesters)

                .backlogs(backlogs)

                .passed(backlogs == 0)

                .build();
    }
}