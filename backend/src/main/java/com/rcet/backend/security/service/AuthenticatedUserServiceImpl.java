package com.rcet.backend.security.service;

import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.User;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.repository.StudentRepository;
import com.rcet.backend.repository.TeacherRepository;
import com.rcet.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserServiceImpl
        implements AuthenticatedUserService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public AuthenticatedUserServiceImpl(
            UserRepository userRepository,
            TeacherRepository teacherRepository,
            StudentRepository studentRepository) {

        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Logged in user not found."));
    }

    @Override
    public Teacher getCurrentTeacher() {

        User user = getCurrentUser();

        return teacherRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher not found."));
    }

    @Override
    public Student getCurrentStudent() {

        User user = getCurrentUser();

        return studentRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found."));
    }
}