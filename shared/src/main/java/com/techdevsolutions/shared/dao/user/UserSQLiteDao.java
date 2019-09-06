package com.techdevsolutions.shared.dao.user;

import com.techdevsolutions.shared.beans.Filter;
import com.techdevsolutions.shared.beans.auditable.User;
import com.techdevsolutions.shared.dao.DaoSQLCrudInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserSQLiteDao implements DaoSQLCrudInterface<User> {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<User> search(Filter search) throws Exception {
        List<User> list = jdbcTemplate.query(
                "SELECT id, name FROM users",
                (rs, rowNum) -> {
                    User User = new User();
                    User.setName(rs.getString("name"));
                    return User;
                }
        );

        return list;
    }

    @Override
    public User get(String id) throws Exception {
        return null;
    }

    @Override
    public User create(User item) throws Exception {
        return null;
    }

    @Override
    public void remove(String id) throws Exception {

    }

    @Override
    public void delete(String id) throws Exception {

    }

    @Override
    public User update(User item) throws Exception {
        return null;
    }

    @Override
    public void dropTable() throws Exception {
        this.jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
    }

    @Override
    public void createTable() throws Exception {
        jdbcTemplate.execute("CREATE TABLE Users (" +
                "id SERIAL, " +
                "name VARCHAR(255) " +
                ")");

        jdbcTemplate.update("INSERT INTO Users (name) VALUES ('test')");
    }
}
