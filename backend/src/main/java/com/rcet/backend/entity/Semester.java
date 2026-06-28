package com.rcet.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "semesters")
public class Semester extends BaseEntity {

    @Column(nullable = false)
    private Integer semesterNumber;

    @Column(length = 200)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    public Semester() {
    }

    public Semester(Integer semesterNumber,
                    String description,
                    Branch branch) {

        this.semesterNumber = semesterNumber;
        this.description = description;
        this.branch = branch;
    }

    public Integer getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(Integer semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}