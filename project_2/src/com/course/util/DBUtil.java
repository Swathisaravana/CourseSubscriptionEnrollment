package com.course.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    private static Connection con;

    public static Connection getDBConnection() {

        try {
            if (con == null || con.isClosed()) {

               
                Class.forName("oracle.jdbc.OracleDriver");

                String url = "jdbc:oracle:thin:@localhost:1521:xe";
                String user = "system";
                String pass = "swathi418";

                con = DriverManager.getConnection(url, user, pass);

               
                con.setAutoCommit(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}
