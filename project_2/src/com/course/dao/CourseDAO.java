package com.course.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.course.bean.Course;

public class CourseDAO {

    public Course findCourse(Connection con, String courseID) throws Exception {

        Course course = null;
        String sql = "SELECT * FROM COURSE_TBL WHERE COURSE_ID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, courseID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            course = new Course();
            course.setCourseID(rs.getString("COURSE_ID"));
            course.setTitle(rs.getString("TITLE"));
            course.setPrice(rs.getDouble("PRICE"));
            course.setTotalSeats(rs.getInt("TOTAL_SEATS"));
            course.setAvailableSeats(rs.getInt("AVAILABLE_SEATS"));
            course.setStartDate(rs.getDate("START_DATE"));
        }

        rs.close();
        ps.close();
        return course;
    }

    public List<Course> viewAllCourses(Connection con) throws Exception {

        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM COURSE_TBL";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Course c = new Course();
            c.setCourseID(rs.getString("COURSE_ID"));
            c.setTitle(rs.getString("TITLE"));
            c.setPrice(rs.getDouble("PRICE"));
            c.setTotalSeats(rs.getInt("TOTAL_SEATS"));
            c.setAvailableSeats(rs.getInt("AVAILABLE_SEATS"));
            c.setStartDate(rs.getDate("START_DATE"));
            list.add(c);
        }

        rs.close();
        ps.close();
        return list;
    }

    public boolean insertCourse(Connection con, Course course) throws Exception {

        String sql = "INSERT INTO COURSE_TBL VALUES (?,?,?,?,?,?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, course.getCourseID());
        ps.setString(2, course.getTitle());
        ps.setDouble(3, course.getPrice());
        ps.setInt(4, course.getTotalSeats());
        ps.setInt(5, course.getAvailableSeats());
        ps.setDate(6, new java.sql.Date(course.getStartDate().getTime()));

        boolean result = ps.executeUpdate() > 0;
        ps.close();
        return result;
    }

    public boolean updateAvailableSeats(Connection con, String courseID, int newCount) throws Exception {

        String sql = "UPDATE COURSE_TBL SET AVAILABLE_SEATS = ? WHERE COURSE_ID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, newCount);
        ps.setString(2, courseID);

        boolean result = ps.executeUpdate() > 0;
        ps.close();
        return result;
    }

    public boolean deleteCourse(Connection con, String courseID) throws Exception {

        String sql = "DELETE FROM COURSE_TBL WHERE COURSE_ID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, courseID);

        boolean result = ps.executeUpdate() > 0;
        ps.close();
        return result;
    }
}
