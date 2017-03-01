package com.mytests.course.ee.jdbc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class SpringJdbcTemplateExample {

    private static final Logger log = LoggerFactory.getLogger(SpringJdbcTemplateExample.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"springContext.xml"});
        JdbcTemplate jdbc = context.getBean("jdbcTemplate", JdbcTemplate.class);

        queryForSimpleObject(jdbc);
    }

    private static void separator(String title) {
        log.debug("\r\n\r\n\r\n\r\n");
    }

    private static void queryForSimpleObject(JdbcTemplate jdbc) {
        separator("queryForSimpleObject");
        int countOfRegion = jdbc.queryForObject("select count(*) from test ", Integer.class);
//        int countOfRegion = jdbc.queryForObject("select count(*) from test where region_name=?", new String[]{"Moscow"}, Integer.class);
        log.debug("Region count:{}",countOfRegion);

//        String regionName = jdbc.queryForObject("select user_name from test where region_id=? and region_name=?", new Object[]{1, "Moscow"}, String.class);
//        log.debug("Region name: {}",regionName);
    }
}
