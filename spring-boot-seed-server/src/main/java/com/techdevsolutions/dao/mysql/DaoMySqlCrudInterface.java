package com.techdevsolutions.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

interface DaoMySqlCrudInterface<T> {
    T rowMapper(ResultSet rs) throws SQLException;

    T get(Integer id) throws Exception;

    void delete(Integer id) throws Exception;

    T create(T item) throws Exception;

    T update(T item) throws Exception;

    List<T> getAll() throws Exception;
}
