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
    }

    private static void separator(String title) {
        log.debug("\r\n\r\n\r\n\r\n");
    }

    private static void queryForSimpleObject(JdbcTemplate jdbc) {

    }
}
