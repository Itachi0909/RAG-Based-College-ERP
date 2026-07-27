package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.MarksBulkRequest;
import com.rcet.backend.dto.response.MarksBulkResponse;
import com.rcet.backend.dto.response.StudentMarksResponse;
import com.rcet.backend.dto.response.TeacherSubjectResultResponse;
import com.rcet.backend.entity.*;
import com.rcet.backend.enums.ResultStatus;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.*;
import com.rcet.backend.security.service.AuthenticatedUserService;
import com.rcet.backend.service.TeacherResultService;
import com.rcet.backend.util.GradeCalculator;
import org.springframework.stereotype.Service;
import com.rcet.backend.dto.request.MarkEntryRequest;
import com.rcet.backend.entity.SemesterResult;
import com.rcet.backend.entity.Student;
import com.rcet.backend.util.MarksValidationUtil;
import java.util.List;
import java.math.BigDecimal;

@Service
public class TeacherResultServiceImpl
        implements TeacherResultService {

    private final TeacherSubjectRepository teacherSubjectRepository;
    private final StudentRepository studentRepository;
    private final SemesterResultRepository semesterResultRepository;
    private final SubjectResultRepository subjectResultRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final SemesterSubjectRepository semesterSubjectRepository;

    public TeacherResultServiceImpl(
            TeacherSubjectRepository teacherSubjectRepository,
            StudentRepository studentRepository,
            SemesterResultRepository semesterResultRepository,
            SubjectResultRepository subjectResultRepository,
            SemesterSubjectRepository semesterSubjectRepository,
            AuthenticatedUserService authenticatedUserService) {

        this.teacherSubjectRepository = teacherSubjectRepository;
        this.studentRepository = studentRepository;
        this.semesterResultRepository = semesterResultRepository;
        this.subjectResultRepository = subjectResultRepository;
        this.semesterSubjectRepository = semesterSubjectRepository;
        this.authenticatedUserService = authenticatedUserService;

    }

    @Override
    public List<TeacherSubjectResultResponse> getMySubjects() {

        Teacher teacher =
                authenticatedUserService.getCurrentTeacher();

        List<TeacherSubject> teacherSubjects =
                teacherSubjectRepository.findByTeacher(teacher);

        return teacherSubjects.stream()
                .map(this::mapTeacherSubject)
                .toList();
    }

    private TeacherSubjectResultResponse mapTeacherSubject(
            TeacherSubject teacherSubject) {

        SemesterSubject semesterSubject =
                teacherSubject.getSemesterSubject();

        Semester semester =
                semesterSubject.getSemester();

        Branch branch =
                semester.getBranch();

        Subject subject =
                semesterSubject.getSubject();

        long studentCount =
                studentRepository.countByBranchAndSemester(
                        branch,
                        semester);

        TeacherSubjectResultResponse response =
                new TeacherSubjectResultResponse();

        response.setTeacherSubjectId(
                teacherSubject.getId());

        response.setSubjectCode(
                subject.getSubjectCode());

        response.setSubjectName(
                subject.getSubjectName());

        response.setSemester(
                semester.getSemesterNumber());

        response.setBranch(
                branch.getName());

        response.setCredits(
                subject.getCredits());

        response.setStudentsCount(
                (int) studentCount);

        response.setStatus("READY");

        return response;
    }

    @Override
    public List<StudentMarksResponse> getStudentsForMarks(
            Long teacherSubjectId) {

        Teacher teacher =
                authenticatedUserService.getCurrentTeacher();

        TeacherSubject teacherSubject =
                teacherSubjectRepository
                        .findByIdAndTeacher(
                                teacherSubjectId,
                                teacher)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subject not assigned to logged in teacher."));

        SemesterSubject semesterSubject =
                teacherSubject.getSemesterSubject();

        Semester semester =
                semesterSubject.getSemester();

        Branch branch =
                semester.getBranch();

        List<Student> students =
                studentRepository.findByBranchAndSemester(
                        branch,
                        semester);

        return students.stream()
                .map(student -> {

                    StudentMarksResponse response =
                            new StudentMarksResponse();

                    response.setStudentId(
                            student.getId());

                    response.setEnrollmentNumber(
                            student.getEnrollmentNumber());

                    response.setRollNumber(
                            student.getRollNumber());

                    response.setStudentName(
                            student.getFirstName() + " " +
                                    student.getLastName());

                    response.setMaxInternalMarks(
                            semesterSubject.getInternalMarks());

                    response.setMaxTheoryMarks(
                            semesterSubject.getExternalMarks());

                    response.setPassingMarks(
                            semesterSubject.getPassingMarks());

                    response.setTotalMarks(
                            semesterSubject.getTotalMarks());

                    semesterResultRepository
                            .findByStudentAndSemester(
                                    student,
                                    semester)
                            .ifPresent(semesterResult ->

                                    subjectResultRepository
                                            .findBySemesterResultAndSemesterSubject(
                                                    semesterResult,
                                                    semesterSubject)
                                            .ifPresent(subjectResult -> {

                                                response.setInternalMarks(
                                                        subjectResult.getInternalMarks());

                                                response.setTheoryMarks(
                                                        subjectResult.getTheoryMarks());

                                                response.setGrade(
                                                        subjectResult.getGrade());
                                            })
                            );

                    return response;

                })
                .toList();
    }
    private SubjectResult saveOrUpdateSubjectResult(
            SemesterResult semesterResult,
            SemesterSubject semesterSubject,
            Integer internalMarks,
            Integer theoryMarks) {

        SubjectResult subjectResult =
                subjectResultRepository
                        .findBySemesterResultAndSemesterSubject(
                                semesterResult,
                                semesterSubject)
                        .orElse(new SubjectResult());

        if (subjectResult.getId() != null &&
                subjectResult.getStatus() == ResultStatus.SUBMITTED) {

            throw new IllegalArgumentException(
                    "Marks already submitted. Editing is not allowed.");
        }

        subjectResult.setSemesterResult(
                semesterResult);

        subjectResult.setSemesterSubject(
                semesterSubject);

        subjectResult.setInternalMarks(
                internalMarks);

        subjectResult.setTheoryMarks(
                theoryMarks);

        subjectResult.setGrade(
                GradeCalculator.calculateGrade(
                        internalMarks,
                        theoryMarks,
                        semesterSubject));

        return subjectResultRepository.save(
                subjectResult);
    }

    @Override
    public MarksBulkResponse saveMarks(
            MarksBulkRequest request) {

        Teacher teacher =
                authenticatedUserService.getCurrentTeacher();

        TeacherSubject teacherSubject =
                teacherSubjectRepository
                        .findByIdAndTeacher(
                                request.getTeacherSubjectId(),
                                teacher)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Subject not assigned to logged in teacher."));

        SemesterSubject semesterSubject =
                teacherSubject.getSemesterSubject();

        Semester semester =
                semesterSubject.getSemester();

        int savedRecords = 0;

        for (MarkEntryRequest mark : request.getMarks()) {

            Student student =
                    studentRepository.findById(
                                    mark.getStudentId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Student not found."));

            SemesterResult semesterResult =
                    semesterResultRepository
                            .findByStudentAndSemester(
                                    student,
                                    semester)
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Semester Result not initialized."));

            MarksValidationUtil.validate(
                    semesterSubject,
                    mark.getInternalMarks(),
                    mark.getTheoryMarks());

            saveOrUpdateSubjectResult(
                    semesterResult,
                    semesterSubject,
                    mark.getInternalMarks(),
                    mark.getTheoryMarks());

            savedRecords++;
        }

        MarksBulkResponse response =
                new MarksBulkResponse();

        response.setTeacherSubjectId(
                request.getTeacherSubjectId());

        response.setTotalStudents(
                request.getMarks().size());

        response.setSavedRecords(
                savedRecords);

        response.setMessage(
                "Marks saved successfully.");

        return response;
    }

    @Override
    public void submitMarks(
            Long teacherSubjectId) {

        Teacher teacher =
                authenticatedUserService.getCurrentTeacher();

        TeacherSubject teacherSubject =
                teacherSubjectRepository
                        .findByIdAndTeacher(
                                teacherSubjectId,
                                teacher)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subject not assigned to logged in teacher."));

        SemesterSubject semesterSubject =
                teacherSubject.getSemesterSubject();

        List<SubjectResult> subjectResults =
                subjectResultRepository
                        .findBySemesterSubject(
                                semesterSubject);

        if (subjectResults.isEmpty()) {
            throw new IllegalArgumentException(
                    "No marks found for this subject.");
        }

        for (SubjectResult subjectResult : subjectResults) {

            if (subjectResult.getStatus() == ResultStatus.SUBMITTED) {
                continue;
            }

            subjectResult.setStatus(ResultStatus.SUBMITTED);

            subjectResultRepository.save(subjectResult);

            SemesterResult semesterResult =
                    subjectResult.getSemesterResult();

            long submittedSubjects =
                    subjectResultRepository
                            .countBySemesterResultAndStatus(
                                    semesterResult,
                                    ResultStatus.SUBMITTED);

            long totalSubjects =
                    semesterSubjectRepository
                            .findBySemester(
                                    semesterResult.getSemester())
                            .size();

            if (submittedSubjects == totalSubjects) {

                calculateSemesterResult(semesterResult);

                semesterResult.setStatus(
                        ResultStatus.SUBMITTED);

            } else {

                semesterResult.setStatus(
                        ResultStatus.IN_PROGRESS);
            }

            semesterResultRepository.save(
                    semesterResult);
        }
    }

    @Override
    public List<StudentMarksResponse> viewMarks(
            Long teacherSubjectId) {

        throw new UnsupportedOperationException(
                "Not implemented yet.");
    }
    private void calculateSemesterResult(
            SemesterResult semesterResult) {

        List<SubjectResult> subjectResults =
                subjectResultRepository.findBySemesterResult(
                        semesterResult);

        int totalObtainedMarks = 0;
        int totalMaximumMarks = 0;

        int totalCredits = 0;
        int earnedGradePoints = 0;

        for (SubjectResult subjectResult : subjectResults) {

            SemesterSubject semesterSubject =
                    subjectResult.getSemesterSubject();

            Subject subject =
                    semesterSubject.getSubject();

            int obtained =
                    subjectResult.getInternalMarks()
                            + subjectResult.getTheoryMarks();

            totalObtainedMarks += obtained;

            totalMaximumMarks +=
                    semesterSubject.getTotalMarks();

            totalCredits +=
                    subject.getCredits();

            earnedGradePoints +=
                    subject.getCredits()
                            * subjectResult
                            .getGrade()
                            .getGradePoint();
        }

        BigDecimal percentage =
                BigDecimal.valueOf(
                        (double) totalObtainedMarks
                                * 100
                                / totalMaximumMarks);

        BigDecimal sgpa =
                BigDecimal.valueOf(
                        (double) earnedGradePoints
                                / totalCredits);

        semesterResult.setPercentage(
                percentage);

        semesterResult.setSgpa(
                sgpa);
    }
}