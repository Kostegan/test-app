package com.mytests.course.ee.jdbc;


import com.mytests.course.ee.jdbc.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Main {
//    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        List<String> stringsList = new ArrayList<String>();
        stringsList.add("One");
        stringsList.add("Two");
        stringsList.add("Three");
        stringsList.add("Four");
        System.out.println(stringsList.toString());
    }
}
