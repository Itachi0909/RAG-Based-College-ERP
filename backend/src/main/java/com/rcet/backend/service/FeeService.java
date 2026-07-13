package com.rcet.backend.service;

import com.rcet.backend.dto.request.FeePaymentRequest;
import com.rcet.backend.dto.request.FeeRequest;
import com.rcet.backend.dto.response.FeeResponse;

import java.util.List;

public interface FeeService {

    FeeResponse createFee(
            FeeRequest request);

    List<FeeResponse> getAllFees();

    FeeResponse getFeeById(
            Long id);

    FeeResponse updateFee(
            Long id,
            FeeRequest request);

    // Replace the old payFee() with this one
    FeeResponse payFee(
            Long id,
            FeePaymentRequest request);

    List<FeeResponse> getStudentFees(
            Long studentId);

    void deleteFee(
            Long id);
}