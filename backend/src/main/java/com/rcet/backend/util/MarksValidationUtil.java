package com.rcet.backend.util;

import com.rcet.backend.entity.SemesterSubject;

public final class MarksValidationUtil {

    private MarksValidationUtil() {
    }

    public static void validate(
            SemesterSubject semesterSubject,
            Integer internalMarks,
            Integer theoryMarks) {

        if (internalMarks < 0) {
            throw new IllegalArgumentException(
                    "Internal marks cannot be negative.");
        }

        if (theoryMarks < 0) {
            throw new IllegalArgumentException(
                    "Theory marks cannot be negative.");
        }

        if (internalMarks > semesterSubject.getInternalMarks()) {
            throw new IllegalArgumentException(
                    "Internal marks cannot exceed "
                            + semesterSubject.getInternalMarks());
        }

        if (theoryMarks > semesterSubject.getExternalMarks()) {
            throw new IllegalArgumentException(
                    "Theory marks cannot exceed "
                            + semesterSubject.getExternalMarks());
        }
    }
}