package com.techdevsolutions.shared.dao;

public interface DaoSQLCrudInterface<T> extends DaoCrudInterface<T> {
    void dropTable() throws Exception;
    void createTable() throws Exception;
}
