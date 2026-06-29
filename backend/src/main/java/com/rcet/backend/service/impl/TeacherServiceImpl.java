package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.TeacherRequest;
import com.rcet.backend.dto.response.TeacherResponse;
import com.rcet.backend.entity.Branch;
import com.rcet.backend.entity.Department;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.BranchRepository;
import com.rcet.backend.repository.DepartmentRepository;
import com.rcet.backend.repository.TeacherRepository;
import com.rcet.backend.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;
    private final BranchRepository branchRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository,
                              DepartmentRepository departmentRepository,
                              BranchRepository branchRepository) {

        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.branchRepository = branchRepository;
    }
    private void validateTeacher(TeacherRequest request) {

        if (teacherRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        if (teacherRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone already exists.");
        }

        if (teacherRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new DuplicateResourceException("Employee ID already exists.");
        }
    }
    @Override
    public TeacherResponse createTeacher(TeacherRequest request) {

        validateTeacher(request);

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found."));

        Teacher teacher = Teacher.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .photoUrl(request.getPhotoUrl())
                .email(request.getEmail())
                .phone(request.getPhone())
                .employeeId(request.getEmployeeId())
                .qualification(request.getQualification())
                .designation(request.getDesignation())
                .status(request.getStatus())
                .joiningDate(request.getJoiningDate())
                .active(request.getActive() == null ? true : request.getActive())
                .department(department)
                .branch(branch)
                .build();

        Teacher savedTeacher = teacherRepository.save(teacher);

        return mapToResponse(savedTeacher);
    }
    @Override
    public List<TeacherResponse> getAllTeachers() {

        return teacherRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public TeacherResponse getTeacherById(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher not found."));

        return mapToResponse(teacher);
    }
    @Override
    public void deleteTeacher(Long id) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher not found."));

        teacherRepository.delete(teacher);
    }
    @Override
    public TeacherResponse updateTeacher(Long id, TeacherRequest request) {

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Teacher not found."));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found."));

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found."));

        teacherRepository.findByEmail(request.getEmail())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException("Email already exists.");
                    }
                });

        teacherRepository.findByPhone(request.getPhone())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException("Phone already exists.");
                    }
                });

        teacherRepository.findByEmployeeId(request.getEmployeeId())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException("Employee ID already exists.");
                    }
                });

        teacher.setFirstName(request.getFirstName());
        teacher.setMiddleName(request.getMiddleName());
        teacher.setLastName(request.getLastName());
        teacher.setGender(request.getGender());
        teacher.setDateOfBirth(request.getDateOfBirth());
        teacher.setPhotoUrl(request.getPhotoUrl());

        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());

        teacher.setEmployeeId(request.getEmployeeId());
        teacher.setQualification(request.getQualification());
        teacher.setDesignation(request.getDesignation());
        teacher.setStatus(request.getStatus());
        teacher.setJoiningDate(request.getJoiningDate());

        teacher.setActive(request.getActive());

        teacher.setDepartment(department);
        teacher.setBranch(branch);

        Teacher updatedTeacher = teacherRepository.save(teacher);

        return mapToResponse(updatedTeacher);
    }
    private TeacherResponse mapToResponse(Teacher teacher) {

        return TeacherResponse.builder()
                .id(teacher.getId())

                .firstName(teacher.getFirstName())
                .middleName(teacher.getMiddleName())
                .lastName(teacher.getLastName())
                .gender(teacher.getGender())
                .dateOfBirth(teacher.getDateOfBirth())
                .photoUrl(teacher.getPhotoUrl())

                .email(teacher.getEmail())
                .phone(teacher.getPhone())

                .employeeId(teacher.getEmployeeId())
                .qualification(teacher.getQualification())
                .designation(teacher.getDesignation())
                .status(teacher.getStatus())
                .joiningDate(teacher.getJoiningDate())

                .departmentId(teacher.getDepartment().getId())
                .departmentName(teacher.getDepartment().getName())

                .branchId(teacher.getBranch().getId())
                .branchName(teacher.getBranch().getName())

                .active(teacher.getActive())

                .build();
    }
}