package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.AssignmentRequest;
import com.rcet.backend.dto.response.AssignmentResponse;
import com.rcet.backend.entity.Assignment;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.TeacherSubject;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.AssignmentRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.repository.TeacherRepository;
import com.rcet.backend.repository.TeacherSubjectRepository;
import com.rcet.backend.service.AssignmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public AssignmentServiceImpl(
            AssignmentRepository assignmentRepository,
            TeacherSubjectRepository teacherSubjectRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository) {

        this.assignmentRepository = assignmentRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public AssignmentResponse createAssignment(
            AssignmentRequest request) {

        TeacherSubject teacherSubject =
                teacherSubjectRepository.findById(
                                request.getTeacherSubjectId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher Subject not found."));

        Assignment assignment = Assignment.builder()

                .teacherSubject(teacherSubject)

                .title(request.getTitle())

                .description(request.getDescription())

                .assignmentType(request.getAssignmentType())

                .dueDate(request.getDueDate())

                .maximumMarks(request.getMaximumMarks())

                .build();

        Assignment saved =
                assignmentRepository.save(assignment);

        return mapToResponse(saved);
    }

    @Override
    public List<AssignmentResponse> getAllAssignments() {

        return assignmentRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentResponse getAssignmentById(
            Long id) {

        Assignment assignment =
                assignmentRepository.findByIdAndActiveTrue(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Assignment not found."));

        return mapToResponse(assignment);
    }

    @Override
    public AssignmentResponse updateAssignment(
            Long id,
            AssignmentRequest request) {

        Assignment assignment = assignmentRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Assignment not found."));

        TeacherSubject teacherSubject =
                teacherSubjectRepository.findById(
                                request.getTeacherSubjectId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher Subject not found."));

        if (request.getDueDate().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Due date cannot be in the past.");
        }

        assignment.setTeacherSubject(teacherSubject);
        assignment.setTitle(request.getTitle());
        assignment.setDescription(request.getDescription());
        assignment.setAssignmentType(request.getAssignmentType());
        assignment.setDueDate(request.getDueDate());
        assignment.setMaximumMarks(request.getMaximumMarks());

        Assignment updated =
                assignmentRepository.save(assignment);

        return mapToResponse(updated);
    }

    @Override
    public void deleteAssignment(
            Long id) {

        Assignment assignment =
                assignmentRepository.findByIdAndActiveTrue(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Assignment not found."));

        assignment.setActive(false);

        assignmentRepository.save(assignment);
    }

    @Override
    public List<AssignmentResponse> getAssignmentsByStudent(
            Long studentId) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Student not found."));

        return assignmentRepository
                .findByTeacherSubject_SemesterSubject_Semester_IdAndActiveTrue(
                        student.getSemester().getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AssignmentResponse> getAssignmentsByTeacher(
            Long teacherId) {

        Teacher teacher =
                teacherRepository.findById(teacherId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Teacher not found."));

        return assignmentRepository
                .findByTeacherSubject_TeacherAndActiveTrue(teacher)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AssignmentResponse mapToResponse(
            Assignment assignment) {

        return AssignmentResponse.builder()

                .id(assignment.getId())

                // ==========================
                // Teacher
                // ==========================

                .teacherId(
                        assignment.getTeacherSubject()
                                .getTeacher()
                                .getId())

                .teacherName(
                        assignment.getTeacherSubject()
                                .getTeacher()
                                .getFirstName()
                                + " "
                                + assignment.getTeacherSubject()
                                .getTeacher()
                                .getLastName())

                // ==========================
                // Teacher Subject
                // ==========================

                .teacherSubjectId(
                        assignment.getTeacherSubject()
                                .getId())

                // ==========================
                // Semester
                // ==========================

                .semesterId(
                        assignment.getTeacherSubject()
                                .getSemesterSubject()
                                .getSemester()
                                .getId())

                .semesterNumber(
                        assignment.getTeacherSubject()
                                .getSemesterSubject()
                                .getSemester()
                                .getSemesterNumber())

                // ==========================
                // Branch
                // ==========================

                .branchId(
                        assignment.getTeacherSubject()
                                .getSemesterSubject()
                                .getSemester()
                                .getBranch()
                                .getId())

                .branchName(
                        assignment.getTeacherSubject()
                                .getSemesterSubject()
                                .getSemester()
                                .getBranch()
                                .getName())

                // ==========================
                // Subject
                // ==========================

                .subjectId(
                        assignment.getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getId())

                .subjectCode(
                        assignment.getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getSubjectCode())

                .subjectName(
                        assignment.getTeacherSubject()
                                .getSemesterSubject()
                                .getSubject()
                                .getSubjectName())

                // ==========================
                // Assignment
                // ==========================

                .title(assignment.getTitle())

                .description(assignment.getDescription())

                .assignmentType(assignment.getAssignmentType())

                .dueDate(assignment.getDueDate())

                .maximumMarks(assignment.getMaximumMarks())

                .active(assignment.getActive())

                .build();
    }
}