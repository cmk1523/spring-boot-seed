package com.techdevsolutions.dao.mysql;

import java.util.List;

public interface DaoMySqlCrudInterface<T> {
    T get(Integer id) throws Exception;

    void delete(Integer id) throws Exception;

    T create(T item) throws Exception;

    T update(T item) throws Exception;

    List<T> getAll() throws Exception;
}
