package com.course.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.course.bean.Enrollment;

public class EnrollmentDAO {

    public int generateEnrollmentID(Connection con) throws Exception {

        String sql = "SELECT NVL(MAX(ENROLLMENT_ID),150000)+1 FROM ENROLLMENT_TBL";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        int id = 0;
        if (rs.next()) {
            id = rs.getInt(1);
        }

        rs.close();
        ps.close();
        return id;
    }

    public boolean recordEnrollment(Connection con, Enrollment e) throws Exception {

        String sql = "INSERT INTO ENROLLMENT_TBL VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, e.getEnrollmentID());
        ps.setString(2, e.getCourseID());
        ps.setString(3, e.getStudentID());
        ps.setString(4, e.getStudentName());
        ps.setDouble(5, e.getAmountPaid());
        ps.setDate(6, new java.sql.Date(e.getEnrollmentDate().getTime()));
        ps.setString(7, e.getStatus());

        boolean result = ps.executeUpdate() > 0;
        ps.close();
        return result;
    }

    public boolean cancelEnrollment(Connection con, int enrollmentID, boolean issueRefund) throws Exception {

        String status = issueRefund ? "REFUNDED" : "CANCELLED";
        String sql = "UPDATE ENROLLMENT_TBL SET STATUS = ? WHERE ENROLLMENT_ID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, enrollmentID);

        boolean result = ps.executeUpdate() > 0;
        ps.close();
        return result;
    }

    public boolean hasActiveEnrollments(Connection con, String courseID) throws Exception {

        String sql = "SELECT 1 FROM ENROLLMENT_TBL WHERE COURSE_ID = ? AND STATUS = 'ENROLLED'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, courseID);
        ResultSet rs = ps.executeQuery();

        boolean exists = rs.next();
        rs.close();
        ps.close();
        return exists;
    }

    public Enrollment findEnrollment(Connection con, int enrollmentID) throws Exception {

        Enrollment enroll = null;
        String sql = "SELECT * FROM ENROLLMENT_TBL WHERE ENROLLMENT_ID = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, enrollmentID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            enroll = new Enrollment();
            enroll.setEnrollmentID(rs.getInt("ENROLLMENT_ID"));
            enroll.setCourseID(rs.getString("COURSE_ID"));
            enroll.setStatus(rs.getString("STATUS"));
        }

        rs.close();
        ps.close();
        return enroll;
    }
}

