package com.rcet.backend.util;

import com.rcet.backend.entity.SemesterSubject;
import com.rcet.backend.enums.Grade;

public final class GradeCalculator {

    private GradeCalculator() {
    }

    public static Grade calculateGrade(
            int internalMarks,
            int theoryMarks,
            SemesterSubject semesterSubject) {

        int obtainedMarks = internalMarks + theoryMarks;

        double percentage =
                (obtainedMarks * 100.0)
                        / semesterSubject.getTotalMarks();

        if (percentage >= 90) {
            return Grade.O;
        }

        if (percentage >= 80) {
            return Grade.A_PLUS;
        }

        if (percentage >= 70) {
            return Grade.A;
        }

        if (percentage >= 60) {
            return Grade.B_PLUS;
        }

        if (percentage >= 50) {
            return Grade.B;
        }

        if (percentage >= 40) {
            return Grade.C;
        }

        return Grade.F;
    }
}