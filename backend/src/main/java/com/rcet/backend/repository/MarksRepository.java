package com.rcet.backend.repository;

import com.rcet.backend.entity.ExamType;
import com.rcet.backend.entity.Marks;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {

    boolean existsByStudentAndTeacherSubjectAndExamType(
            Student student,
            TeacherSubject teacherSubject,
            ExamType examType
    );

    List<Marks> findByStudent(Student student);

    List<Marks> findByTeacherSubject(TeacherSubject teacherSubject);

    List<Marks> findByStudentAndTeacherSubject(
            Student student,
            TeacherSubject teacherSubject
    );
}