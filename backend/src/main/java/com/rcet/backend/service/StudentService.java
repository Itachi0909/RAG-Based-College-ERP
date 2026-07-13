package com.rcet.backend.service;

import com.rcet.backend.dto.request.StudentRequest;
import com.rcet.backend.dto.response.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse createStudent(StudentRequest request);

    StudentResponse getStudentById(Long id);

    List<StudentResponse> getAllStudents();

    StudentResponse updateStudent(Long id,
                                  StudentRequest request);

    void deleteStudent(Long id);
}