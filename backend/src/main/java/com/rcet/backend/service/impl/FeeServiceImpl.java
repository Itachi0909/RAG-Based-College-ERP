package com.rcet.backend.service.impl;

import com.rcet.backend.dto.request.FeePaymentRequest;
import com.rcet.backend.dto.request.FeeRequest;
import com.rcet.backend.dto.response.FeeResponse;
import com.rcet.backend.entity.Fee;
import com.rcet.backend.entity.FeeStatus;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.Student;
import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.FeeRepository;
import com.rcet.backend.repository.SemesterRepository;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.service.FeeService;
import org.springframework.stereotype.Service;
import com.rcet.backend.security.service.AuthenticatedUserService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeeServiceImpl implements FeeService {

    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final AuthenticatedUserService authenticatedUserService;

    public FeeServiceImpl(
            FeeRepository feeRepository,
            StudentRepository studentRepository,
            SemesterRepository semesterRepository,
            AuthenticatedUserService authenticatedUserService) {

        this.feeRepository = feeRepository;
        this.studentRepository = studentRepository;
        this.semesterRepository = semesterRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Override
    public FeeResponse createFee(
            FeeRequest request) {

        Student student = studentRepository.findById(
                        request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."));

        Semester semester = semesterRepository.findById(
                        request.getSemesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester not found."));
        if (!student.getSemester().getId().equals(semester.getId())) {
            throw new IllegalArgumentException(
                    "Student does not belong to the selected semester.");
        }

        if (feeRepository.existsByStudentAndSemester(
                student,
                semester)) {

            throw new DuplicateResourceException(
                    "Fee already exists for this semester.");
        }

        Fee fee = Fee.builder()

                .student(student)

                .semester(semester)

                .totalAmount(request.getTotalAmount())

                .dueDate(request.getDueDate())

                .remarks(request.getRemarks())

                .build();

        Fee saved = feeRepository.save(fee);

        return mapToResponse(saved);
    }

    @Override
    public List<FeeResponse> getAllFees() {

        return feeRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FeeResponse getFeeById(
            Long id) {

        Fee fee = feeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Fee not found."));

        return mapToResponse(fee);
    }

    @Override
    public FeeResponse updateFee(
            Long id,
            FeeRequest request) {

        Fee fee = feeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Fee not found."));

        Student student = studentRepository.findById(
                        request.getStudentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."));

        Semester semester = semesterRepository.findById(
                        request.getSemesterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Semester not found."));

        if (!student.getSemester().getId().equals(semester.getId())) {
            throw new IllegalArgumentException(
                    "Student does not belong to the selected semester.");
        }

        feeRepository.findByStudentAndSemester(student, semester)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new DuplicateResourceException(
                                "Fee already exists for this semester.");
                    }
                });

        fee.setStudent(student);
        fee.setSemester(semester);
        fee.setTotalAmount(request.getTotalAmount());
        fee.setDueDate(request.getDueDate());
        fee.setRemarks(request.getRemarks());

        Fee updated = feeRepository.save(fee);

        return mapToResponse(updated);
    }

    @Override
    public FeeResponse payFee(
            Long id,
            FeePaymentRequest request) {

        Fee fee = feeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Fee not found."));

        BigDecimal newPaidAmount =
                fee.getPaidAmount().add(request.getAmount());

        if (newPaidAmount.compareTo(fee.getTotalAmount()) > 0) {

            throw new IllegalArgumentException(
                    "Paid amount cannot exceed total amount.");
        }

        fee.setPaidAmount(newPaidAmount);

        fee.setPaymentDate(LocalDate.now());

        if (newPaidAmount.compareTo(fee.getTotalAmount()) == 0) {

            fee.setStatus(FeeStatus.PAID);

        } else {

            fee.setStatus(FeeStatus.PARTIALLY_PAID);
        }

        if (request.getRemarks() != null &&
                !request.getRemarks().isBlank()) {

            fee.setRemarks(request.getRemarks());
        }

        Fee updated = feeRepository.save(fee);

        return mapToResponse(updated);
    }

    @Override
    public List<FeeResponse> getStudentFees() {

        Student student =
                authenticatedUserService.getCurrentStudent();

        return feeRepository.findByStudentAndActiveTrue(student)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFee(
            Long id) {

        Fee fee = feeRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Fee not found."));

        fee.setActive(false);

        feeRepository.save(fee);
    }

    private FeeResponse mapToResponse(
            Fee fee) {

        BigDecimal dueAmount =
                fee.getTotalAmount().subtract(fee.getPaidAmount());

        return FeeResponse.builder()

                .id(fee.getId())

                // ==========================
                // Student
                // ==========================

                .studentId(fee.getStudent().getId())

                .enrollmentNumber(
                        fee.getStudent().getEnrollmentNumber())

                .studentName(
                        fee.getStudent().getFirstName()
                                + " "
                                + fee.getStudent().getLastName())

                // ==========================
                // Semester
                // ==========================

                .semesterId(fee.getSemester().getId())

                .semesterNumber(
                        fee.getSemester().getSemesterNumber())

                // ==========================
                // Fee
                // ==========================

                .totalAmount(fee.getTotalAmount())

                .paidAmount(fee.getPaidAmount())

                .dueAmount(dueAmount)

                .dueDate(fee.getDueDate())

                .paymentDate(fee.getPaymentDate())

                .status(fee.getStatus())

                .remarks(fee.getRemarks())

                .active(fee.getActive())

                .build();
    }
}