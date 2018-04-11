package com.techdevsolutions.dao.mysql.user;

import com.techdevsolutions.beans.Search;
import com.techdevsolutions.beans.auditable.User;
import com.techdevsolutions.dao.DaoCrudInterface;
import com.techdevsolutions.dao.mysql.BaseMySqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserMySqlDao extends BaseMySqlDao implements DaoCrudInterface<User> {
    private static final String TABLE_NAME = "users";
    private static final String INSERT_FIELDS = "NAME, CREATEDBY, UPDATEDBY, CREATEDDATE, UPDATEDDATE, REMOVED";
    private static final String SELECT_FIELDS = "ID, " + INSERT_FIELDS;

    @Autowired
    public UserMySqlDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public List<User> search(Search search) throws Exception {
        logger.info("UserMySqlDao - search");

        // TODO: Search...

        return new ArrayList<>();
    }

    public List<User> getAll() throws Exception {
        logger.info("UserMySqlDao - getAll");
        final String sql = "SELECT " + UserMySqlDao.SELECT_FIELDS +
                " FROM " + UserMySqlDao.TABLE_NAME +
                " WHERE removed = 0";
        return this.jdbcTemplate.query(sql, new Object[] {}, new UserRowMapper());
    }

    public User get(Integer id) throws Exception {
        logger.info("UserMySqlDao - get - id: " + id);
        final String sql = "SELECT " + UserMySqlDao.SELECT_FIELDS +
                " FROM " + UserMySqlDao.TABLE_NAME +
                " WHERE removed = 0 and id = ?";

        try {
            return this.jdbcTemplate.queryForObject(sql, new Object[] {id}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void remove(Integer id) throws Exception {
        logger.info("UserMySqlDao - remove - id: " + id);
        final String sql = "UPDATE " + UserMySqlDao.TABLE_NAME +
                " SET removed=1" +
                " WHERE id = ?";
        this.jdbcTemplate.update(sql, new Object[] {id});
    }

    public void delete(Integer id) throws Exception {
        logger.info("UserMySqlDao - delete - id: " + id);
        final String sql = "DELETE FROM " + UserMySqlDao.TABLE_NAME +
                " WHERE id = ?";
        this.jdbcTemplate.update(sql, new Object[] {id});
    }

    @Override
    public User update(User item) throws Exception {
        logger.info("UserMySqlDao - update - id: " + item.getId());
        final String sql = "UPDATE " + UserMySqlDao.TABLE_NAME +
                " SET name=?, createdBy=?, updatedBy=?, createdDate=?, updatedDate=?" +
                " WHERE id = ?";
        this.jdbcTemplate.update(sql, new Object[] {item.getId()});
        return this.get(item.getId());
    }

    public User create(User item) throws Exception {
        logger.info("UserMySqlDao - create - id: " + item.getId());
        final String sql = "INSERT INTO " + UserMySqlDao.TABLE_NAME + "" +
                " (" + UserMySqlDao.INSERT_FIELDS + ")" +
                " VALUES (?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, item.getName());
                ps.setString(2, item.getCreatedBy());
                ps.setString(3, item.getUpdatedBy());
                ps.setLong(4, item.getCreatedDate());
                ps.setLong(5, item.getUpdatedDate());
                ps.setBoolean(6, item.getRemoved());
                return ps;
            }
        }, keyHolder);

        item.setId(keyHolder.getKey().intValue());

        if (item.getId() != null) {
            logger.info("UserDao - create - Created item - id: " + item.getId() + ", name: " + item.getName());
            return item;
        } else {
            throw new Exception("Unable to get ID of inserted item");
        }
    }
}
