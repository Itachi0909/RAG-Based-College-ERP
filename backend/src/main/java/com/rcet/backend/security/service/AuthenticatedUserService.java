package com.rcet.backend.security.service;

import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.User;

public interface AuthenticatedUserService {

    User getCurrentUser();

    Teacher getCurrentTeacher();

    Student getCurrentStudent();

}