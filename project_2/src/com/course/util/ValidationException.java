package com.course.util;

public class ValidationException extends RuntimeException {

    @Override
    public String toString() {
        return "ValidationException: Invalid input values detected "
             + "(empty ID, invalid amount, or invalid date).";
    }
}

