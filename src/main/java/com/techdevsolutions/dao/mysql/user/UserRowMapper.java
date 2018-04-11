package com.techdevsolutions.dao.mysql.user;

import com.techdevsolutions.beans.auditable.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int arg1) throws SQLException {
        User i = new User();
        i.setId(rs.getInt("id"));
        i.setName(rs.getString("name"));
        i.setCreatedBy(rs.getString("createdBy"));
        i.setCreatedDate(rs.getLong("createdDate"));
        i.setUpdatedBy(rs.getString("updatedBy"));
        i.setUpdatedDate(rs.getLong("updatedDate"));
        i.setRemoved(rs.getBoolean("removed"));
        return i;
    }
}
