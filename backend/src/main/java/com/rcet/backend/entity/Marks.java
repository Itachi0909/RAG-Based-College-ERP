package com.rcet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "marks",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "student_id",
                                "teacher_subject_id",
                                "exam_type"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marks extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_subject_id", nullable = false)
    private TeacherSubject teacherSubject;

    @Enumerated(EnumType.STRING)
    @Column(name = "exam_type", nullable = false)
    private ExamType examType;

    @Column(nullable = false)
    private Double maximumMarks;

    @Column(nullable = false)
    private Double obtainedMarks;

    @Column(nullable = false)
    private Double weightage;

    @Column(length = 255)
    private String remarks;
}