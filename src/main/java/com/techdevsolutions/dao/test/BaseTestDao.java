package com.techdevsolutions.dao.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.logging.Logger;

public class BaseTestDao {
    protected Logger logger = Logger.getLogger(BaseTestDao.class.getName());

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public BaseTestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
