package com.techdevsolutions.files.dao.file;

import com.techdevsolutions.files.beans.auditable.File;
import com.techdevsolutions.shared.beans.Filter;
import com.techdevsolutions.shared.dao.DaoSQLCrudInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileSQLiteDao implements DaoSQLCrudInterface<File> {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<File> search(Filter search) throws Exception {
        List<File> list = jdbcTemplate.query(
                "SELECT id, name FROM files",
                (rs, rowNum) -> {
                    File file = new File();
                    file.setName(rs.getString("name"));
                    return file;
                }
        );

        return list;
    }

    @Override
    public File get(String id) throws Exception {
        return null;
    }

    @Override
    public File create(File item) throws Exception {
        return null;
    }

    @Override
    public void remove(String id) throws Exception {

    }

    @Override
    public void delete(String id) throws Exception {

    }

    @Override
    public File update(File item) throws Exception {
        return null;
    }

    @Override
    public void dropTable() throws Exception {
        this.jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
    }

    @Override
    public void createTable() throws Exception {
        jdbcTemplate.execute("CREATE TABLE files (" +
                "id SERIAL, " +
                "name VARCHAR(255) " +
                ")");

        jdbcTemplate.update("INSERT INTO files (name) VALUES ('test')");
    }
}
