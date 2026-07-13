package com.rcet.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "teacher_subjects",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "teacher_id",
                                "semester_subject_id"
                        }
                )
        }
)
public class TeacherSubject extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "teacher_id",
            nullable = false
    )
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "semester_subject_id",
            nullable = false
    )
    private SemesterSubject semesterSubject;

    public TeacherSubject() {
    }

    public TeacherSubject(
            Teacher teacher,
            SemesterSubject semesterSubject) {

        this.teacher = teacher;
        this.semesterSubject = semesterSubject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(
            Teacher teacher) {
        this.teacher = teacher;
    }

    public SemesterSubject getSemesterSubject() {
        return semesterSubject;
    }

    public void setSemesterSubject(
            SemesterSubject semesterSubject) {
        this.semesterSubject = semesterSubject;
    }
}