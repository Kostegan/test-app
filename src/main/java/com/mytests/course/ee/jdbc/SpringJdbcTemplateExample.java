package com.mytests.course.ee.jdbc;


import com.mytests.course.ee.jdbc.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class SpringJdbcTemplateExample {

    private static final Logger log = LoggerFactory.getLogger(SpringJdbcTemplateExample.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"springContext.xml"});
        JdbcTemplate jdbc = context.getBean("jdbcTemplate", JdbcTemplate.class);

//        queryForSimpleObject(jdbc);
//        queryForComplexObject(jdbc);
//        insertNewRegion(jdbc);
        insertConstructor(jdbc);

    }

    private static void separator(String title) {
        log.debug("\r\n\r\n\r\n\r\n");
        log.debug("=========={}==========", title);
//        log.debug("");
    }

    private static void queryForSimpleObject(JdbcTemplate jdbc) {
        separator("queryForSimpleObject");
        int countOfRegion = jdbc.queryForObject("select count(*) from js_region where region_name=?", new String[]{"Moscow"}, Integer.class);
        log.debug("Region count:{}", countOfRegion);

        String regionName = jdbc.queryForObject("select region_name from js_region where id=? and region_name=?", new Object[]{2, "Moscow"}, String.class);
        log.debug("Region name: {}", regionName);
    }

    private static void queryForComplexObject(JdbcTemplate jdbc) {
        separator("queryForSimpleObject");
        Region region = jdbc.queryForObject("select id, region_name from js_region where id=? and region_name=?", new Object[]{4, "Voronegskaya Obl"}, new RowMapper<Region>() {
            @Override
            public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Region(rs.getInt("id"), rs.getString("region_name"));
            }
        });
        log.debug("Region = {}", region);
    }

    private static void insertNewRegion(JdbcTemplate jdbc) {
        separator("insertNewRegion");
        PreparedStatementCreatorFactory creatorFactory = new PreparedStatementCreatorFactory("insert into js_region (region_name) values (?)");
        creatorFactory.setGeneratedKeysColumnNames("id");
        creatorFactory.addParameter(new SqlParameter(Types.VARCHAR));
        PreparedStatementCreator preparedStatementCreator = creatorFactory.newPreparedStatementCreator(new String[]{"HMAO"});
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int insertCount = jdbc.update(preparedStatementCreator, keyHolder);
        log.debug("Insert count: {}", insertCount);
        log.debug("New Region's id: {}", keyHolder.getKey().intValue());
    }

    private static void insertConstructor(JdbcTemplate jdbc) {
        separator("insertConstructor");
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbc).withTableName("js_region").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("region_name", "new region name2");
//        insertActor.execute(parameters);
        Number number = insertActor.executeAndReturnKey(parameters);
//        log.debug("Inserted region id: {}", number.longValue());
    }
}
