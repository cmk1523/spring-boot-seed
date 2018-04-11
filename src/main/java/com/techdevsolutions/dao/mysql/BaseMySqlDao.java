package com.techdevsolutions.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.logging.Logger;

public class BaseMySqlDao  {
    protected Logger logger = Logger.getLogger(BaseMySqlDao.class.getName());

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public BaseMySqlDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
