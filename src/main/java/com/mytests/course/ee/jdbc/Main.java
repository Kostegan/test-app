package com.mytests.course.ee.jdbc;


import com.mytests.course.ee.jdbc.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 *
 */
public class Main {
//    private static final Logger log = LoggerFactory.getLogger(Main.class);





    private  static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws SQLException {
        Region region =(Region) new Test<Region>() {
            Object object = null;
            @Override
            public Object actionForTest() {
                object = new Region(1,"Voroneg");
                return object;
            }
        }.actionForTest();

        log.debug("Print region: {}",region.getRegionName());
    }
}

interface Test<T>{
    Object actionForTest();
}
