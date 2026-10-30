package com.rcet.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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

    @Column(
            name = "active",
            nullable = false
    )
    private Boolean active = true;

    public TeacherSubject() {
    }

    public TeacherSubject(
            Teacher teacher,
            SemesterSubject semesterSubject) {

        this.teacher = teacher;
        this.semesterSubject = semesterSubject;
        this.active = true;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public SemesterSubject getSemesterSubject() {
        return semesterSubject;
    }

    public void setSemesterSubject(
            SemesterSubject semesterSubject) {

        this.semesterSubject = semesterSubject;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}