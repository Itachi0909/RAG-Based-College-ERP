package com.rcet.backend.repository;

import com.rcet.backend.entity.Subject;
import com.rcet.backend.entity.Teacher;
import com.rcet.backend.entity.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject, Long> {

    boolean existsByTeacherAndSubject(Teacher teacher, Subject subject);

    List<TeacherSubject> findByTeacher(Teacher teacher);

    List<TeacherSubject> findBySubject(Subject subject);
}