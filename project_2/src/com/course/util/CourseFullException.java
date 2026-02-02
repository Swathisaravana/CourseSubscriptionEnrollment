package com.course.util;

public class CourseFullException extends RuntimeException {

    @Override
    public String toString() {
        return "CourseFullException: No available seats for this course.";
    }
}
