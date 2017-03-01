package com.mytests.course.ee.jdbc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 *
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JavaCourse2","root","root");
            ps = con.prepareStatement("select user_name from test");
            rs = ps.executeQuery();
            while(rs.next()){
                log.debug("First name - {}",rs.getString("user_name"));
            }
        } catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
