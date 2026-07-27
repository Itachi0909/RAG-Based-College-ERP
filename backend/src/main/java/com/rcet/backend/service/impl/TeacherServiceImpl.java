package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.TeacherRequest;
import com.rcet.backend.dto.response.TeacherResponse;
import com.rcet.backend.entity.Branch;
import com.rcet.backend.entity.Department;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.User;
import com.rcet.backend.enums.Role;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.mapper.TeacherMapper;
import com.rcet.backend.repository.BranchRepository;
import com.rcet.backend.repository.DepartmentRepository;
import com.rcet.backend.repository.TeacherRepository;
import com.rcet.backend.repository.UserRepository;
import com.rcet.backend.service.TeacherService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherServiceImpl(
            TeacherRepository teacherRepository,
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            BranchRepository branchRepository,
            PasswordEncoder passwordEncoder) {

        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.branchRepository = branchRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public TeacherResponse createTeacher(TeacherRequest request) {

        validateDuplicates(request);

        Department department = getDepartment(request.getDepartmentId());

        Branch branch = getBranch(request.getBranchId());

        if (!branch.getDepartment().getId().equals(department.getId())) {
            throw new IllegalArgumentException(
                    "Selected branch does not belong to the selected department.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Role.TEACHER)
                .enabled(true)
                .build();

        user = userRepository.save(user);

        Teacher teacher = TeacherMapper.toEntity(request);

        teacher.setDepartment(department);
        teacher.setBranch(branch);
        teacher.setUser(user);

        Teacher savedTeacher = teacherRepository.save(teacher);

        return TeacherMapper.toResponse(savedTeacher);
    }

    @Override
    public TeacherResponse getTeacherById(Long id) {

        return TeacherMapper.toResponse(getTeacher(id));
    }

    @Override
    public List<TeacherResponse> getAllTeachers() {

        return teacherRepository.findByActiveTrue()
                .stream()
                .map(TeacherMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherResponse updateTeacher(
            Long id,
            TeacherRequest request) {

        Teacher teacher = getTeacher(id);

        if (!teacher.getEmail().equals(request.getEmail())
                && teacherRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists.");
        }

        if (!teacher.getPhone().equals(request.getPhone())
                && teacherRepository.existsByPhone(request.getPhone())) {

            throw new DuplicateResourceException(
                    "Phone number already exists.");
        }

        if (!teacher.getEmployeeId().equals(request.getEmployeeId())
                && teacherRepository.existsByEmployeeId(request.getEmployeeId())) {

            throw new DuplicateResourceException(
                    "Employee ID already exists.");
        }

        Department department = getDepartment(request.getDepartmentId());

        Branch branch = getBranch(request.getBranchId());

        if (!branch.getDepartment().getId().equals(department.getId())) {
            throw new IllegalArgumentException(
                    "Selected branch does not belong to the selected department.");
        }

        TeacherMapper.updateEntity(teacher, request);

        teacher.setDepartment(department);
        teacher.setBranch(branch);

        User user = teacher.getUser();

        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        Teacher updatedTeacher = teacherRepository.save(teacher);

        return TeacherMapper.toResponse(updatedTeacher);
    }

    @Override
    public void deleteTeacher(Long id) {

        Teacher teacher = getTeacher(id);

        User user = teacher.getUser();

        teacher.setActive(false);

        user.setEnabled(false);

        teacherRepository.save(teacher);
        userRepository.save(user);
    }

    // ===================================================
    // Helper Methods
    // ===================================================

    private Teacher getTeacher(Long id) {

        return teacherRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher not found."));
    }

    private Department getDepartment(Long id) {

        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department not found."));
    }

    private Branch getBranch(Long id) {

        return branchRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Branch not found."));
    }

    private void validateDuplicates(
            TeacherRequest request) {

        if (teacherRepository.existsByEmail(request.getEmail())
                || userRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists.");
        }

        if (teacherRepository.existsByPhone(request.getPhone())
                || userRepository.existsByPhone(request.getPhone())) {

            throw new DuplicateResourceException(
                    "Phone number already exists.");
        }

        if (teacherRepository.existsByEmployeeId(
                request.getEmployeeId())) {

            throw new DuplicateResourceException(
                    "Employee ID already exists.");
        }
    }
}