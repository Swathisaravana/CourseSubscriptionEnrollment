package com.course.service;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.course.bean.Course;
import com.course.bean.Enrollment;
import com.course.dao.CourseDAO;
import com.course.dao.EnrollmentDAO;
import com.course.util.*;

public class EnrollmentService {

    private CourseDAO courseDAO = new CourseDAO();
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    public Course viewCourseDetails(String courseID) throws Exception {

        if (courseID == null || courseID.isEmpty())
            throw new ValidationException();

        Connection con = DBUtil.getDBConnection();
        return courseDAO.findCourse(con, courseID);
    }

    public List<Course> viewAllCourses() throws Exception {

        Connection con = DBUtil.getDBConnection();
        return courseDAO.viewAllCourses(con);
    }

    public boolean addNewCourse(Course c) throws Exception {

        if (c.getCourseID() == null || c.getTitle().isEmpty()
                || c.getPrice() < 0 || c.getTotalSeats() <= 0)
            throw new ValidationException();

        Connection con = DBUtil.getDBConnection();

        try {
            boolean result = courseDAO.insertCourse(con, c);
            con.commit();
            return result;
        } catch (Exception e) {
            con.rollback();
            throw e;
        }
    }

    public boolean removeCourse(String courseID) throws Exception {

        Connection con = DBUtil.getDBConnection();

        if (enrollmentDAO.hasActiveEnrollments(con, courseID))
            throw new ActiveEnrollmentException();

        try {
            boolean result = courseDAO.deleteCourse(con, courseID);
            con.commit();
            return result;
        } catch (Exception e) {
            con.rollback();
            throw e;
        }
    }

    public boolean enrollStudent(String courseID, String studentID,
                                 String studentName, double paymentAmount,
                                 Date enrollDate) throws Exception {

        Connection con = DBUtil.getDBConnection();

        try {
            Course course = courseDAO.findCourse(con, courseID);

            if (course == null)
                return false;

            if (course.getAvailableSeats() <= 0)
                throw new CourseFullException();

            if (paymentAmount < course.getPrice())
                throw new ValidationException();

            int newSeats = course.getAvailableSeats() - 1;
            courseDAO.updateAvailableSeats(con, courseID, newSeats);

            Enrollment e = new Enrollment();
            e.setEnrollmentID(enrollmentDAO.generateEnrollmentID(con));
            e.setCourseID(courseID);
            e.setStudentID(studentID);
            e.setStudentName(studentName);
            e.setAmountPaid(paymentAmount);
            e.setEnrollmentDate(enrollDate);
            e.setStatus("ENROLLED");

            enrollmentDAO.recordEnrollment(con, e);

            con.commit();
            return true;

        } catch (Exception ex) {
            con.rollback();
            throw ex;
        }
    }

    public boolean cancelEnrollment(int enrollmentID, boolean issueRefund) throws Exception {

        if (enrollmentID <= 0)
            throw new ValidationException();

        Connection con = DBUtil.getDBConnection();

        try {
            boolean result = enrollmentDAO.cancelEnrollment(con, enrollmentID, issueRefund);
            con.commit();
            return result;
        } catch (Exception e) {
            con.rollback();
            throw e;
        }
    }
}
