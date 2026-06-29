package com.rcet.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subjects")
public class Subject extends BaseEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String subjectCode;

    @Column(nullable = false, length = 150)
    private String subjectName;

    @Column(length = 300)
    private String description;

    @Column(nullable = false)
    private Integer credits;

    public Subject() {
    }

    public Subject(String subjectCode,
                   String subjectName,
                   String description,
                   Integer credits) {

        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.description = description;
        this.credits = credits;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}