package com.techdevsolutions.dao.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

interface DaoTestCrudInterface<T> {
    T get(Integer id) throws Exception;

    void delete(Integer id) throws Exception;

    T create(T item) throws Exception;

    T update(T item) throws Exception;

    List<T> getAll() throws Exception;
}
