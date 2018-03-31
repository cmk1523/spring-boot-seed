package com.techdevsolutions.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.*;
import java.util.logging.Logger;

public class BaseDao {
    Logger logger = Logger.getLogger(BaseDao.class.getName());

    @Autowired
    Environment environment;

    public PreparedStatement initCreatePreparedStatement(String sql) throws Exception {
        return MySqlConnector
                .getConnection(this.environment)
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public PreparedStatement initQueryPreparedStatement(String sql) throws Exception {
        return MySqlConnector
                .getConnection(this.environment)
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public Integer getInsertedId(PreparedStatement preparedStmt) throws SQLException {
        Integer id = null;
        ResultSet rs = preparedStmt.getGeneratedKeys();
        if (rs.next()){
            id = rs.getInt(1);
        }
        rs.close();
        return id;
    }

    public Boolean doesTableExist(String table) throws Exception {
        DatabaseMetaData dbm = MySqlConnector.getConnection(this.environment).getMetaData();
        ResultSet rs = dbm.getTables(null, null, table, null);
        return rs.next();
    }
}
