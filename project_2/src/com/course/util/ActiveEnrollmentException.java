package com.course.util;

public class ActiveEnrollmentException extends RuntimeException {

    @Override
    public String toString() {
        return "ActiveEnrollmentException: Cannot remove course "
             + "because active enrollments exist.";
    }
}