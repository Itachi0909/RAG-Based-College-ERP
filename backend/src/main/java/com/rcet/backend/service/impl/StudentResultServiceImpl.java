package com.rcet.backend.service.impl;

import com.rcet.backend.dto.response.StudentResultResponse;
import com.rcet.backend.dto.response.StudentSubjectResultResponse;
import com.rcet.backend.entity.SemesterResult;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.SubjectResult;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.SemesterResultRepository;
import com.rcet.backend.repository.SubjectResultRepository;
import com.rcet.backend.security.service.AuthenticatedUserService;
import com.rcet.backend.service.StudentResultService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentResultServiceImpl
        implements StudentResultService {

    private final SemesterResultRepository semesterResultRepository;
    private final SubjectResultRepository subjectResultRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public StudentResultServiceImpl(
            SemesterResultRepository semesterResultRepository,
            SubjectResultRepository subjectResultRepository,
            AuthenticatedUserService authenticatedUserService) {

        this.semesterResultRepository = semesterResultRepository;
        this.subjectResultRepository = subjectResultRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public StudentResultResponse getMyResult() {

        Student student =
                authenticatedUserService.getCurrentStudent();

        SemesterResult semesterResult =
                semesterResultRepository
                        .findByStudentAndStatus(
                                student,
                                com.rcet.backend.enums.ResultStatus.PUBLISHED)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No published result found."));

        List<SubjectResult> subjectResults =
                subjectResultRepository.findBySemesterResult(
                        semesterResult);

        StudentResultResponse response =
                new StudentResultResponse();

        response.setEnrollmentNumber(
                student.getEnrollmentNumber());

        response.setStudentName(
                student.getFirstName()
                        + " "
                        + student.getLastName());

        response.setSemester(
                semesterResult.getSemester()
                        .getSemesterNumber());

        response.setBranch(
                semesterResult.getSemester()
                        .getBranch()
                        .getName());

        response.setSgpa(
                semesterResult.getSgpa());

        response.setPercentage(
                semesterResult.getPercentage());

        response.setStatus(
                semesterResult.getStatus().name());

        List<StudentSubjectResultResponse> subjects =
                subjectResults.stream()
                        .map(this::mapSubjectResult)
                        .toList();

        response.setSubjects(subjects);

        return response;
    }

    private StudentSubjectResultResponse mapSubjectResult(
            SubjectResult subjectResult) {

        StudentSubjectResultResponse response =
                new StudentSubjectResultResponse();

        response.setSubjectCode(
                subjectResult.getSemesterSubject()
                        .getSubject()
                        .getSubjectCode());

        response.setSubjectName(
                subjectResult.getSemesterSubject()
                        .getSubject()
                        .getSubjectName());

        response.setInternalMarks(
                subjectResult.getInternalMarks());

        response.setTheoryMarks(
                subjectResult.getTheoryMarks());

        response.setTotalMarks(
                subjectResult.getInternalMarks()
                        + subjectResult.getTheoryMarks());

        response.setGrade(
                subjectResult.getGrade());

        return response;
    }
}