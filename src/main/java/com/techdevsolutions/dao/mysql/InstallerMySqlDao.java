package com.techdevsolutions.dao.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class InstallerMySqlDao extends BaseMySqlDao {
    @Autowired
    public InstallerMySqlDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void createTables() {
        String createUsersTable = "CREATE TABLE `users` (" +
                "`ID` int(16) NOT NULL AUTO_INCREMENT, " +
                "`NAME` varchar(256) NOT NULL, " +
                "`CREATEDBY` varchar(256) NOT NULL, " +
                "`UPDATEDBY` varchar(256) NOT NULL, " +
                "`CREATEDDATE` bigint NOT NULL, " +
                "`UPDATEDDATE` bigint NOT NULL, " +
                "`REMOVED` tinyint(1) NOT NULL, " +
                "PRIMARY KEY (`ID`)) " +
                "ENGINE=InnoDB AUTO_INCREMENT=1 " +
                "DEFAULT CHARSET=utf8";
        this.jdbcTemplate.execute(createUsersTable);
    }
}
