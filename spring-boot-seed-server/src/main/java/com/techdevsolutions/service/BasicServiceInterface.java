package com.techdevsolutions.service;

import java.util.List;

interface BasicServiceInterface<T> {
    List<T> getAll() throws Exception;
    T get(T item) throws Exception;
    T get(Integer id) throws Exception;
    T create(T item) throws Exception;
    void delete(Integer id) throws Exception;
    T update(T item) throws Exception;
}