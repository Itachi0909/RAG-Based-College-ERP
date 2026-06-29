package com.rcet.backend.service;

import com.rcet.backend.dto.request.MarksRequest;
import com.rcet.backend.dto.response.MarksResponse;

import java.util.List;

public interface MarksService {

    MarksResponse createMarks(MarksRequest request);

    List<MarksResponse> getAllMarks();

    MarksResponse getMarksById(Long id);

    MarksResponse updateMarks(Long id, MarksRequest request);

    void deleteMarks(Long id);
}