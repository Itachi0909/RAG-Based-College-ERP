package com.rcet.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "semester_subjects",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"semester_id", "subject_id"}
                )
        }
)
public class SemesterSubject extends BaseEntity {

    // ==========================
    // Relationships
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    // ==========================
    // Marking Scheme
    // ==========================

    @Column(nullable = false)
    private Integer internalMarks;

    @Column(nullable = false)
    private Integer externalMarks;

    @Column(nullable = false)
    private Integer passingMarks;

    @Column(nullable = false)
    private Integer totalMarks;

    @Column(nullable = false)
    private boolean active = true;

    public SemesterSubject() {
    }

    public SemesterSubject(Semester semester,
                           Subject subject,
                           Integer internalMarks,
                           Integer externalMarks,
                           Integer passingMarks,
                           Integer totalMarks,
                           boolean active) {

        this.semester = semester;
        this.subject = subject;
        this.internalMarks = internalMarks;
        this.externalMarks = externalMarks;
        this.passingMarks = passingMarks;
        this.totalMarks = totalMarks;
        this.active = active;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(Integer internalMarks) {
        this.internalMarks = internalMarks;
    }

    public Integer getExternalMarks() {
        return externalMarks;
    }

    public void setExternalMarks(Integer externalMarks) {
        this.externalMarks = externalMarks;
    }

    public Integer getPassingMarks() {
        return passingMarks;
    }

    public void setPassingMarks(Integer passingMarks) {
        this.passingMarks = passingMarks;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}