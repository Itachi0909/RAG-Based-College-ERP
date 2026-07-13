package com.rcet.backend.repository;

import com.rcet.backend.entity.ExamType;
import com.rcet.backend.entity.Marks;
import com.rcet.backend.entity.Semester;
import com.rcet.backend.entity.Student;
import com.rcet.backend.entity.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {

    boolean existsByStudentAndTeacherSubjectAndExamType(
            Student student,
            TeacherSubject teacherSubject,
            ExamType examType
    );

    Optional<Marks> findByStudentAndTeacherSubjectAndExamType(
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

    @Query("""
SELECT m
FROM Marks m
WHERE m.student.id = :studentId
AND m.teacherSubject.semesterSubject.semester.id = :semesterId
""")
    List<Marks> findByStudentAndSemester(
            @Param("studentId") Long studentId,
            @Param("semesterId") Long semesterId
    );
}