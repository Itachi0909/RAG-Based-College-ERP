package com.rcet.backend.entity;

import com.rcet.backend.enums.Grade;
import com.rcet.backend.enums.ResultStatus;
import jakarta.persistence.*;

@Entity
@Table(
        name = "subject_results",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "semester_result_id",
                                "semester_subject_id"
                        }
                )
        }
)
public class SubjectResult extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "semester_result_id",
            nullable = false
    )
    private SemesterResult semesterResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "semester_subject_id",
            nullable = false
    )
    private SemesterSubject semesterSubject;

    @Column(nullable = false)
    private Integer internalMarks;

    @Column(nullable = false)
    private Integer theoryMarks;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultStatus status = ResultStatus.DRAFT;

    public SubjectResult() {
    }

    public SemesterResult getSemesterResult() {
        return semesterResult;
    }

    public void setSemesterResult(SemesterResult semesterResult) {
        this.semesterResult = semesterResult;
    }

    public SemesterSubject getSemesterSubject() {
        return semesterSubject;
    }

    public void setSemesterSubject(SemesterSubject semesterSubject) {
        this.semesterSubject = semesterSubject;
    }

    public Integer getInternalMarks() {
        return internalMarks;
    }

    public void setInternalMarks(Integer internalMarks) {
        this.internalMarks = internalMarks;
    }

    public Integer getTheoryMarks() {
        return theoryMarks;
    }

    public void setTheoryMarks(Integer theoryMarks) {
        this.theoryMarks = theoryMarks;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }
}