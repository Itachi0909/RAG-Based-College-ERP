package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.StudentRequest;
import com.rcet.backend.dto.response.StudentResponse;
import com.rcet.backend.entity.Branch;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.Student;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.BranchRepository;
import com.rcet.backend.repository.SemesterRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final BranchRepository branchRepository;
    private final SemesterRepository semesterRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              BranchRepository branchRepository,
                              SemesterRepository semesterRepository) {

        this.studentRepository = studentRepository;
        this.branchRepository = branchRepository;
        this.semesterRepository = semesterRepository;
    }

    private void validateStudent(StudentRequest request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        if (studentRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone already exists.");
        }

        if (studentRepository.existsByEnrollmentNumber(request.getEnrollmentNumber())) {
            throw new DuplicateResourceException("Enrollment number already exists.");
        }

        if (studentRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new DuplicateResourceException("Registration number already exists.");
        }

        if (studentRepository.existsByRollNumber(request.getRollNumber())) {
            throw new DuplicateResourceException("Roll number already exists.");
        }
    }
    @Override
    public StudentResponse createStudent(StudentRequest request) {

        validateStudent(request);

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found."));

        Semester semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Semester not found."));

        Student student = Student.builder()
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .bloodGroup(request.getBloodGroup())
                .photoUrl(request.getPhotoUrl())
                .email(request.getEmail())
                .phone(request.getPhone())
                .enrollmentNumber(request.getEnrollmentNumber())
                .registrationNumber(request.getRegistrationNumber())
                .rollNumber(request.getRollNumber())
                .admissionYear(request.getAdmissionYear())
                .status(request.getStatus())
                .currentAddress(request.getCurrentAddress())
                .currentCity(request.getCurrentCity())
                .currentState(request.getCurrentState())
                .currentPincode(request.getCurrentPincode())
                .permanentAddress(request.getPermanentAddress())
                .permanentCity(request.getPermanentCity())
                .permanentState(request.getPermanentState())
                .permanentPincode(request.getPermanentPincode())
                .guardianName(request.getGuardianName())
                .guardianPhone(request.getGuardianPhone())
                .guardianRelation(request.getGuardianRelation())
                .active(request.getActive() == null ? true : request.getActive())
                .branch(branch)
                .semester(semester)
                .build();

        Student savedStudent = studentRepository.save(student);

        return mapToResponse(savedStudent);
    }
    @Override
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public StudentResponse getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found."));

        return mapToResponse(student);
    }
    @Override
    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found."));

        studentRepository.delete(student);
    }
    @Override
    public StudentResponse updateStudent(Long id, StudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found."));

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Branch not found."));

        Semester semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Semester not found."));

        studentRepository.findByEmail(request.getEmail())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException("Email already exists.");
                    }
                });

        studentRepository.findByPhone(request.getPhone())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException("Phone already exists.");
                    }
                });

        studentRepository.findByEnrollmentNumber(request.getEnrollmentNumber())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException("Enrollment number already exists.");
                    }
                });

        studentRepository.findByRegistrationNumber(request.getRegistrationNumber())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException("Registration number already exists.");
                    }
                });

        studentRepository.findByRollNumber(request.getRollNumber())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException("Roll number already exists.");
                    }
                });

        student.setFirstName(request.getFirstName());
        student.setMiddleName(request.getMiddleName());
        student.setLastName(request.getLastName());
        student.setGender(request.getGender());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setBloodGroup(request.getBloodGroup());
        student.setPhotoUrl(request.getPhotoUrl());

        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());

        student.setEnrollmentNumber(request.getEnrollmentNumber());
        student.setRegistrationNumber(request.getRegistrationNumber());
        student.setRollNumber(request.getRollNumber());

        student.setAdmissionYear(request.getAdmissionYear());
        student.setStatus(request.getStatus());

        student.setCurrentAddress(request.getCurrentAddress());
        student.setCurrentCity(request.getCurrentCity());
        student.setCurrentState(request.getCurrentState());
        student.setCurrentPincode(request.getCurrentPincode());

        student.setPermanentAddress(request.getPermanentAddress());
        student.setPermanentCity(request.getPermanentCity());
        student.setPermanentState(request.getPermanentState());
        student.setPermanentPincode(request.getPermanentPincode());

        student.setGuardianName(request.getGuardianName());
        student.setGuardianPhone(request.getGuardianPhone());
        student.setGuardianRelation(request.getGuardianRelation());

        student.setActive(request.getActive());

        student.setBranch(branch);
        student.setSemester(semester);

        Student updatedStudent = studentRepository.save(student);

        return mapToResponse(updatedStudent);
    }
    private StudentResponse mapToResponse(Student student) {

        return StudentResponse.builder()
                .id(student.getId())

                // Personal Information
                .firstName(student.getFirstName())
                .middleName(student.getMiddleName())
                .lastName(student.getLastName())
                .gender(student.getGender())
                .dateOfBirth(student.getDateOfBirth())
                .bloodGroup(student.getBloodGroup())
                .photoUrl(student.getPhotoUrl())

                // Contact Information
                .email(student.getEmail())
                .phone(student.getPhone())

                // Academic Information
                .enrollmentNumber(student.getEnrollmentNumber())
                .registrationNumber(student.getRegistrationNumber())
                .rollNumber(student.getRollNumber())
                .admissionYear(student.getAdmissionYear())
                .status(student.getStatus())

                // Current Address
                .currentAddress(student.getCurrentAddress())
                .currentCity(student.getCurrentCity())
                .currentState(student.getCurrentState())
                .currentPincode(student.getCurrentPincode())

                // Permanent Address
                .permanentAddress(student.getPermanentAddress())
                .permanentCity(student.getPermanentCity())
                .permanentState(student.getPermanentState())
                .permanentPincode(student.getPermanentPincode())

                // Guardian
                .guardianName(student.getGuardianName())
                .guardianPhone(student.getGuardianPhone())
                .guardianRelation(student.getGuardianRelation())

                // Branch
                .branchId(student.getBranch().getId())
                .branchName(student.getBranch().getName())

                // Semester
                .semesterId(student.getSemester().getId())
                .semesterNumber(student.getSemester().getSemesterNumber())

                // Status
                .active(student.getActive())

                .build();
    }
}