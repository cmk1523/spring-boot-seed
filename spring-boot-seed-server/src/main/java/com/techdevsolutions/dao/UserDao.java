package com.techdevsolutions.dao;

import com.techdevsolutions.beans.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDao extends BaseDao implements DaoInterface<User> {
    private static final String TABLE_NAME = "USERS";
    private static final String INSERT_FIELDS = "NAME, CREATEDBY, UPDATEDBY, CREATEDDATE, UPDATEDDATE";
    private static final String SELECT_FIELDS = "ID, " + INSERT_FIELDS;

    public User rowMapper(ResultSet rs) throws SQLException {
        User item = new User();
        item.setId(rs.getInt("ID"));
        item.setName(rs.getString("NAME"));
        item.setCreatedBy(rs.getString("CREATEDBY"));
        item.setCreatedDate(rs.getLong("CREATEDDATE"));
        item.setUpdatedBy(rs.getString("UPDATEDBY"));
        item.setUpdatedDate(rs.getLong("UPDATEDDATE"));
        return item;
    }

    public List<User> getAll() throws Exception {
        final String sql = "SELECT " + UserDao.SELECT_FIELDS + " FROM " + UserDao.TABLE_NAME;
        PreparedStatement ps = this.initQueryPreparedStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<User> list = new ArrayList<>();

        while (rs.next()) {
            User item = this.rowMapper(rs);
            list.add(item);
        }

        return list;
    }

    public User get(Integer id) throws Exception {
        final String sql = "SELECT " + UserDao.SELECT_FIELDS + " FROM " + UserDao.TABLE_NAME + " WHERE id = ?";
        PreparedStatement ps = this.initQueryPreparedStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        User item = null;

        while (rs.next()) {
            item = this.rowMapper(rs);
        }

        return item;
    }

    public void delete(Integer id) throws Exception {
        final String sql = "DELETE FROM " + UserDao.TABLE_NAME + " WHERE id = ?";
        PreparedStatement ps = this.initQueryPreparedStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public User create(User item) throws Exception {
        if (!this.doesTableExist(UserDao.TABLE_NAME)) {
            // TODO: Create table...
        }

        final String sql = "INSERT INTO " + UserDao.TABLE_NAME + " (" + UserDao.INSERT_FIELDS + ") " +
                "VALUES (?,?,?,?,?)";
        PreparedStatement ps = this.initCreatePreparedStatement(sql);
        ps.setString(1, item.getName());
        ps.setString(2, item.getCreatedBy());
        ps.setString(3, item.getUpdatedBy());
        ps.setBigDecimal(4, new BigDecimal(item.getCreatedDate()));
        ps.setBigDecimal(5, new BigDecimal(item.getUpdatedDate()));

        ps.execute();

        item.setId(this.getInsertedId(ps));

        if (item.getId() != null) {
            logger.info("UserDao - create - Created item - id: " + item.getId() + ", path: " + item.getName());
            return item;
        } else {
            throw new Exception("Unable to get ID of inserted item");
        }
    }

    @Override
    public User update(User user) throws Exception {
        return null;
    }
}
