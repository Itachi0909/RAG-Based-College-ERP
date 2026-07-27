package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.StudentRequest;
import com.rcet.backend.dto.response.StudentResponse;
import com.rcet.backend.entity.Branch;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.User;
import com.rcet.backend.enums.Role;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.mapper.StudentMapper;
import com.rcet.backend.repository.BranchRepository;
import com.rcet.backend.repository.SemesterRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.repository.UserRepository;
import com.rcet.backend.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final SemesterRepository semesterRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(
            StudentRepository studentRepository,
            UserRepository userRepository,
            BranchRepository branchRepository,
            SemesterRepository semesterRepository,
            PasswordEncoder passwordEncoder) {

        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.branchRepository = branchRepository;
        this.semesterRepository = semesterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===========================================
    // Create Student
    // ===========================================

    @Override
    public StudentResponse createStudent(StudentRequest request) {

        validateDuplicates(request);

        Branch branch = getBranch(request.getBranchId());
        Semester semester = getSemester(request.getSemesterId());

        // Validate that semester belongs to the selected branch
        if (!semester.getBranch().getId().equals(branch.getId())) {
            throw new IllegalArgumentException(
                    "Selected semester does not belong to the selected branch.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Role.STUDENT)
                .enabled(true)
                .build();

        user = userRepository.save(user);

        Student student = StudentMapper.toEntity(request);

        student.setBranch(branch);
        student.setSemester(semester);
        student.setUser(user);

        Student savedStudent = studentRepository.save(student);

        return StudentMapper.toResponse(savedStudent);
    }

    // ===========================================
    // Get Student By ID
    // ===========================================

    @Override
    public StudentResponse getStudentById(Long id) {

        return StudentMapper.toResponse(getStudent(id));
    }

    // ===========================================
    // Get All Students
    // ===========================================

    @Override
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findByActiveTrue()
                .stream()
                .map(StudentMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ===========================================
    // Update Student
    // ===========================================

    @Override
    public StudentResponse updateStudent(
            Long id,
            StudentRequest request) {

        Student student = getStudent(id);

        if (!student.getEnrollmentNumber().equals(request.getEnrollmentNumber())
                && studentRepository.existsByEnrollmentNumber(request.getEnrollmentNumber())) {

            throw new DuplicateResourceException(
                    "Enrollment number already exists.");
        }

        if (!student.getRegistrationNumber().equals(request.getRegistrationNumber())
                && studentRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {

            throw new DuplicateResourceException(
                    "Registration number already exists.");
        }

        if (!student.getRollNumber().equals(request.getRollNumber())
                && studentRepository.existsByRollNumber(request.getRollNumber())) {

            throw new DuplicateResourceException(
                    "Roll number already exists.");
        }

        if (!student.getEmail().equals(request.getEmail())
                && studentRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists.");
        }

        if (!student.getPhone().equals(request.getPhone())
                && studentRepository.existsByPhone(request.getPhone())) {

            throw new DuplicateResourceException(
                    "Phone number already exists.");
        }

        Branch branch = getBranch(request.getBranchId());
        Semester semester = getSemester(request.getSemesterId());

        // Validate that semester belongs to the selected branch
        if (!semester.getBranch().getId().equals(branch.getId())) {
            throw new IllegalArgumentException(
                    "Selected semester does not belong to the selected branch.");
        }

        StudentMapper.updateEntity(student, request);

        student.setBranch(branch);
        student.setSemester(semester);

        User user = student.getUser();

        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        Student updatedStudent =
                studentRepository.save(student);

        return StudentMapper.toResponse(updatedStudent);
    }

    // ===========================================
    // Delete Student
    // ===========================================

    @Override
    public void deleteStudent(Long id) {

        Student student = getStudent(id);

        User user = student.getUser();

        student.setActive(false);

        user.setEnabled(false);

        studentRepository.save(student);
        userRepository.save(user);
    }

    // ===========================================
    // Helper Methods
    // ===========================================

    private Student getStudent(Long id) {

        return studentRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."));
    }

    private Branch getBranch(Long id) {

        return branchRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Branch not found."));
    }

    private Semester getSemester(Long id) {

        return semesterRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester not found."));
    }

    private void validateDuplicates(StudentRequest request) {

        if (studentRepository.existsByEnrollmentNumber(
                request.getEnrollmentNumber())) {

            throw new DuplicateResourceException(
                    "Enrollment number already exists.");
        }

        if (studentRepository.existsByRegistrationNumber(
                request.getRegistrationNumber())) {

            throw new DuplicateResourceException(
                    "Registration number already exists.");
        }

        if (studentRepository.existsByRollNumber(
                request.getRollNumber())) {

            throw new DuplicateResourceException(
                    "Roll number already exists.");
        }

        if (studentRepository.existsByEmail(request.getEmail())
                || userRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists.");
        }

        if (studentRepository.existsByPhone(request.getPhone())
                || userRepository.existsByPhone(request.getPhone())) {

            throw new DuplicateResourceException(
                    "Phone number already exists.");
        }
    }
}