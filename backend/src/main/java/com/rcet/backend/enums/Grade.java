package com.rcet.backend.enums;

public enum Grade {

    O(10),
    A_PLUS(9),
    A(8),
    B_PLUS(7),
    B(6),
    C(5),
    F(0);

    private final int gradePoint;

    Grade(int gradePoint) {
        this.gradePoint = gradePoint;
    }

    public int getGradePoint() {
        return gradePoint;
    }
}