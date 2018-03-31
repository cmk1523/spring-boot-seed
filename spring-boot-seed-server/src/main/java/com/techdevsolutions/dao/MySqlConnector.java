package com.techdevsolutions.dao;

import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnector {
    public static Connection instance = null;

    public static Connection getConnection(Environment environment) throws Exception {
        if (instance == null) {
            String user = environment.getProperty("database.username");
            String password = environment.getProperty("database.password");
            String host = environment.getProperty("database.host");
            String port = environment.getProperty("database.port");
            String database = environment.getProperty("database.database");
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connectionStr = "jdbc:mysql://" + host + ":" + port + "/" + database;
            instance = DriverManager.getConnection(connectionStr, user, password);

            if (instance == null) {
                throw new Exception("MySQL Connection failed");
            }
        }

        return instance;
    }
}
