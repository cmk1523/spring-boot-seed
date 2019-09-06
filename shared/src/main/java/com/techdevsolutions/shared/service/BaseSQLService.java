package com.techdevsolutions.shared.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class BaseSQLService {
    protected Environment environment;

    @Autowired
    public BaseSQLService(Environment environment) {
        this.environment = environment;
    }

//    @Bean
//    public DataSource dataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(this.environment.getProperty("driverClassName"));
//        dataSource.setUrl(this.environment.getProperty("url"));
//        dataSource.setUsername(this.environment.getProperty("user"));
//        dataSource.setPassword(this.environment.getProperty("password"));
//        return dataSource;
//    }
}
