package com.rcet.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "teacher_subjects",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "teacher_id",
                                "subject_id"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherSubject extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
}