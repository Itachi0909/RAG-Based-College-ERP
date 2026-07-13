package com.rcet.backend.util;

public final class GradeUtil {

    private GradeUtil() {
    }

    public static String getGrade(double percentage) {

        if (percentage >= 90) {
            return "O";
        } else if (percentage >= 80) {
            return "A+";
        } else if (percentage >= 70) {
            return "A";
        } else if (percentage >= 60) {
            return "B+";
        } else if (percentage >= 50) {
            return "B";
        } else if (percentage >= 45) {
            return "C";
        } else {
            return "F";
        }
    }

    public static int getGradePoint(String grade) {

        return switch (grade) {
            case "O" -> 10;
            case "A+" -> 9;
            case "A" -> 8;
            case "B+" -> 7;
            case "B" -> 6;
            case "C" -> 5;
            default -> 0;
        };
    }

    public static boolean isPassed(double percentage) {
        return percentage >= 45;
    }
}