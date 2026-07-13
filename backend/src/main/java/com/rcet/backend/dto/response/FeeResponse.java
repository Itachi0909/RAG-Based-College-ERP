package com.rcet.backend.dto.response;

import com.rcet.backend.entity.FeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeResponse {

    private Long id;

    // Student
    private Long studentId;
    private String enrollmentNumber;
    private String studentName;

    // Semester
    private Long semesterId;
    private Integer semesterNumber;

    // Fee
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private BigDecimal dueAmount;

    private LocalDate dueDate;
    private LocalDate paymentDate;

    private FeeStatus status;

    private String remarks;

    private Boolean active;
}