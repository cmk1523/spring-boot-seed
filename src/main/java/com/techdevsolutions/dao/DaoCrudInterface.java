package com.techdevsolutions.dao;

import java.util.List;

public interface DaoCrudInterface<T> {
    List<T> search() throws Exception;
    List<T> getAll() throws Exception;
    T get(Integer id) throws Exception;
    T create(T item) throws Exception;
    void remove(Integer id) throws Exception;
    void delete(Integer id) throws Exception;
    T update(T item) throws Exception;
}
