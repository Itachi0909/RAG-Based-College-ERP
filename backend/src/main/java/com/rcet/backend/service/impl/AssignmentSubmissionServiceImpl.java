package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.AssignmentEvaluationRequest;
import com.rcet.backend.dto.request.AssignmentSubmissionRequest;
import com.rcet.backend.dto.response.AssignmentSubmissionResponse;
import com.rcet.backend.entity.Assignment;
import com.rcet.backend.entity.AssignmentSubmission;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.SubmissionStatus;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.AssignmentRepository;
import com.rcet.backend.repository.AssignmentSubmissionRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.service.AssignmentSubmissionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentSubmissionServiceImpl
        implements AssignmentSubmissionService {

    private final AssignmentSubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;

    public AssignmentSubmissionServiceImpl(
            AssignmentSubmissionRepository submissionRepository,
            AssignmentRepository assignmentRepository,
            StudentRepository studentRepository) {

        this.submissionRepository = submissionRepository;
        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public AssignmentSubmissionResponse submitAssignment(
            AssignmentSubmissionRequest request) {

        Assignment assignment = assignmentRepository
                .findByIdAndActiveTrue(request.getAssignmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment not found."));

        Student student = studentRepository.findById(
                        request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."));

        if (submissionRepository.existsByAssignmentAndStudent(
                assignment,
                student)) {

            throw new DuplicateResourceException(
                    "Assignment already submitted.");
        }

        AssignmentSubmission submission =
                AssignmentSubmission.builder()

                        .assignment(assignment)

                        .student(student)

                        .submissionText(
                                request.getSubmissionText())

                        .submittedAt(LocalDateTime.now())

                        .status(SubmissionStatus.SUBMITTED)

                        .build();

        AssignmentSubmission saved =
                submissionRepository.save(submission);

        return mapToResponse(saved);
    }

    @Override
    public List<AssignmentSubmissionResponse>
    getStudentSubmissions(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."));

        return submissionRepository
                .findByStudentAndActiveTrue(student)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentSubmissionResponse>
    getAssignmentSubmissions(Long assignmentId) {

        Assignment assignment = assignmentRepository
                .findByIdAndActiveTrue(assignmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment not found."));

        return submissionRepository
                .findByAssignmentAndActiveTrue(assignment)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentSubmissionResponse evaluateSubmission(
            Long submissionId,
            AssignmentEvaluationRequest request) {

        AssignmentSubmission submission =
                submissionRepository
                        .findByIdAndActiveTrue(submissionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission not found."));

        if (request.getObtainedMarks() >
                submission.getAssignment().getMaximumMarks()) {

            throw new IllegalArgumentException(
                    "Obtained marks cannot be greater than maximum marks.");
        }

        submission.setObtainedMarks(
                request.getObtainedMarks());

        submission.setRemarks(
                request.getRemarks());

        submission.setStatus(
                SubmissionStatus.EVALUATED);

        AssignmentSubmission updated =
                submissionRepository.save(submission);

        return mapToResponse(updated);
    }

    @Override
    public void deleteSubmission(
            Long submissionId) {

        AssignmentSubmission submission =
                submissionRepository
                        .findByIdAndActiveTrue(submissionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Submission not found."));

        submission.setActive(false);

        submissionRepository.save(submission);
    }

    private AssignmentSubmissionResponse mapToResponse(
            AssignmentSubmission submission) {

        return AssignmentSubmissionResponse.builder()

                .id(submission.getId())

                // ==========================
                // Student
                // ==========================

                .studentId(
                        submission.getStudent().getId())

                .enrollmentNumber(
                        submission.getStudent().getEnrollmentNumber())

                .studentName(
                        submission.getStudent().getFirstName()
                                + " "
                                + submission.getStudent().getLastName())

                // ==========================
                // Assignment
                // ==========================

                .assignmentId(
                        submission.getAssignment().getId())

                .assignmentTitle(
                        submission.getAssignment().getTitle())

                // ==========================
                // Subject
                // ==========================

                .subjectId(
                        submission.getAssignment()
                                .getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getId())

                .subjectCode(
                        submission.getAssignment()
                                .getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getSubjectCode())

                .subjectName(
                        submission.getAssignment()
                                .getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getSubjectName())

                // ==========================
                // Submission
                // ==========================

                .submissionText(
                        submission.getSubmissionText())

                .attachmentUrl(
                        submission.getAttachmentUrl())

                .submittedAt(
                        submission.getSubmittedAt())

                .status(
                        submission.getStatus())

                // ==========================
                // Evaluation
                // ==========================

                .maximumMarks(
                        submission.getAssignment()
                                .getMaximumMarks())

                .obtainedMarks(
                        submission.getObtainedMarks())

                .remarks(
                        submission.getRemarks())

                .build();
    }
}