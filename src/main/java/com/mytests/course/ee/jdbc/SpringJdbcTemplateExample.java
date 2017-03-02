package com.mytests.course.ee.jdbc;


import com.mytests.course.ee.jdbc.model.City;
import com.mytests.course.ee.jdbc.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpringJdbcTemplateExample {

    private static final Logger log = LoggerFactory.getLogger(SpringJdbcTemplateExample.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"springContext.xml"});
        JdbcTemplate jdbc = context.getBean("jdbcTemplate", JdbcTemplate.class);

//        queryForSimpleObject(jdbc);
//        queryForComplexObject(jdbc);
//        insertNewRegion(jdbc);
//        queryForListComplexObjects(jdbc);
//        batchUpdate(jdbc);
//        insertConstructor(jdbc);
//        manyToOne(jdbc);
//        oneToManyMultipleQueries(jdbc);


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
        Number number = insertActor.executeAndReturnKey(parameters);
        log.debug("Inserted region id: {}", number.longValue());
    }

    private static void manyToOne(JdbcTemplate jdbc) {
        List<City> city = jdbc.query("SELECT c.id AS c_id, c.city_name AS c_name,c.region_id AS r_id, r.region_name AS r_name FROM js_city c INNER JOIN js_region r ON c.region_id=r.id;", new RowMapper<City>() {
            @Override
            public City mapRow(ResultSet rs, int rowNum) throws SQLException {
                Region region = new Region(rs.getInt("r_id"), rs.getString("r_name"));
                City city = new City(rs.getInt("c_id"), rs.getString("c_name"), region);
                return city;
            }
        });
        for (City city1 : city) {
            log.debug("City: {}, region: {}", city1.getCityName(), city1.getRegion().getRegionName());
        }
    }

    private static void queryForListComplexObjects(JdbcTemplate jdbc) {
        separator("queryForListComplexObjects");
        List<Region> list = jdbc.query("SELECT id, region_name FROM js_region;", new RowMapper<Region>() {
            @Override
            public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
                Region region = new Region(rs.getInt("id"), rs.getString("region_name"));
                return region;
            }
        });
        for (Region region : list) {
            log.debug("Region id: {}, region name: {}", region.getRegionId(), region.getRegionName());
        }
    }

    private static void batchUpdate(JdbcTemplate jdbc) {
        separator("batchUpdate");
        final List<Region> regions = new ArrayList<Region>();
        regions.add(new Region("testRegion1"));
        regions.add(new Region("testRegion2"));
        regions.add(new Region("testRegion3"));
        regions.add(new Region("testRegion4"));

        int[] ints = jdbc.batchUpdate("INSERT INTO js_region(region_name) VALUES(?);", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, regions.get(i).getRegionName());
            }

            @Override
            public int getBatchSize() {
                return regions.size();
            }
        });

        for (int i : ints) {
            log.debug("Inserted: {} records", i);
        }
    }

    private static void oneToManyMultipleQueries(JdbcTemplate jdbc) {
        List<Region> regionsList = jdbc.query("SELECT id, region_name FROM js_region;", new RowMapper<Region>() {
            @Override
            public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Region(rs.getInt("id"),rs.getString("region_name"));
            }
        });

        for (final Region region : regionsList) {
            List<City> cities = jdbc.query("SELECT c.id, c.city_name  FROM js_city c WHERE c.region_id=?", new Integer[]{region.getRegionId()}, new RowMapper<City>() {
                @Override
                public City mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new City(rs.getInt("id"),rs.getString("city_name"),region);
                }
            });
            region.setCities(cities);
        }
        for(Region region : regionsList){
            log.debug("Region: {}",region);
        }
    }
}
