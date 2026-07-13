package com.rcet.backend.service.impl;

import com.rcet.backend.dto.response.ResultResponse;
import com.rcet.backend.dto.response.SubjectResultResponse;
import com.rcet.backend.entity.Marks;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.Student;
import com.rcet.backend.repository.MarksRepository;
import com.rcet.backend.repository.SemesterRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.service.ResultService;
import com.rcet.backend.util.GradeUtil;
import com.rcet.backend.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final MarksRepository marksRepository;

    public ResultServiceImpl(
            StudentRepository studentRepository,
            SemesterRepository semesterRepository,
            MarksRepository marksRepository) {

        this.studentRepository = studentRepository;
        this.semesterRepository = semesterRepository;
        this.marksRepository = marksRepository;
    }

    @Override
    public ResultResponse generateResult(
            Long studentId,
            Long semesterId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found."));

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Semester not found."));

        List<Marks> marksList =
                marksRepository.findByStudentAndSemester(
                        studentId,
                        semesterId
                );

        if (marksList.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No marks found for this semester.");
        }

        Map<Long, List<Marks>> subjectWiseMarks =
                marksList.stream()
                        .collect(Collectors.groupingBy(
                                m -> m.getTeacherSubject()
                                        .getSemesterSubject()
                                        .getSubject()
                                        .getId()
                        ));

        List<SubjectResultResponse> subjectResults =
                new ArrayList<>();

        double totalMaximum = 0;
        double totalObtained = 0;

        double totalCreditPoints = 0;
        int totalCredits = 0;

        boolean passed = true;

        for (List<Marks> subjectMarks : subjectWiseMarks.values()) {

            SubjectResultResponse response =
                    buildSubjectResult(subjectMarks);

            subjectResults.add(response);

            totalMaximum += response.getMaximumMarks();
            totalObtained += response.getObtainedMarks();

            if (!response.isPassed()) {
                passed = false;
            }

            totalCreditPoints +=
                    response.getGradePoint()
                            * response.getCredits();

            totalCredits += response.getCredits();
        }

        double percentage =
                (totalObtained / totalMaximum) * 100;

        double sgpa =
                totalCreditPoints / totalCredits;

        return ResultResponse.builder()

                .studentId(student.getId())
                .enrollmentNumber(student.getEnrollmentNumber())
                .studentName(
                        student.getFirstName()
                                + " "
                                + student.getLastName())

                .semesterId(semester.getId())
                .semesterNumber(semester.getSemesterNumber())

                .subjects(subjectResults)

                .totalMaximumMarks(totalMaximum)
                .totalObtainedMarks(totalObtained)
                .percentage(percentage)

                .sgpa(sgpa)


                .grade(
                        GradeUtil.getGrade(percentage))

                .passed(passed)

                .build();
    }

    private SubjectResultResponse buildSubjectResult(
            List<Marks> marksList) {

        Marks first = marksList.get(0);

        double maximumMarks = 0;
        double obtainedMarks = 0;

        for (Marks marks : marksList) {

            maximumMarks += marks.getMaximumMarks();
            obtainedMarks += marks.getObtainedMarks();
        }

        double percentage =
                (obtainedMarks / maximumMarks) * 100;

        String grade = GradeUtil.getGrade(percentage);

        int gradePoint = GradeUtil.getGradePoint(grade);

        boolean passed = GradeUtil.isPassed(percentage);

        return SubjectResultResponse.builder()

                .subjectId(
                        first.getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getId())

                .subjectCode(
                        first.getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getSubjectCode())

                .subjectName(
                        first.getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getSubjectName())

                .credits(
                        first.getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getCredits())

                .maximumMarks(maximumMarks)

                .obtainedMarks(obtainedMarks)

                .percentage(percentage)

                .grade(grade)

                .gradePoint(gradePoint)

                .passed(passed)

                .build();
    }
}