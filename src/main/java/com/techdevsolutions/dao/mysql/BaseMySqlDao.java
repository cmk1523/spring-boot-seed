package com.techdevsolutions.dao.mysql;

import com.techdevsolutions.beans.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.util.StringUtils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class BaseMySqlDao  {
    protected Logger logger = Logger.getLogger(BaseMySqlDao.class.getName());

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public BaseMySqlDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String generateFilters(Search search, List<String> possibleNumericFields) throws Exception {
        List<String> filters = new ArrayList<>();

        if (search.getFilters().length() > 0) {
            filters = Arrays.asList(search.getFilters().split(";;"));
        }

        String sql = "";

        if (filters.size() > 0) {
            sql += " AND (";
        }

        for (String keyValue : filters) {
            if (StringUtils.contains(keyValue, "::")) {
                String[] split = keyValue.split("::");

                if (split.length == 2) {
                    String key = split[0].trim();
                    String value = split[1].trim();

                    // Strings
                    sql += key + " = '" + value + "'";

                    sql = this.addFilterLogic(sql, search);
                } else {
                    throw new Exception("Invalid filters: " + search.getFilters());
                }
            } else if (StringUtils.contains(keyValue, ">")) {
                String[] split = keyValue.split(">");

                if (split.length == 2) {
                    String key = split[0].trim();
                    String value = split[1].trim();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // Dates
                    if (this.containsIgnoreCase(possibleNumericFields, key)) {
                        sql += key + " > '" + sdf.format(new Date(Long.parseLong(value))) + "'";
                    }

                    sql = this.addFilterLogic(sql, search);
                } else {
                    throw new Exception("Invalid filters: " + search.getFilters());
                }
            } else if (StringUtils.contains(keyValue, "<")) {
                String[] split = keyValue.split("<");

                if (split.length == 2) {
                    String key = split[0].trim();
                    String value = split[1].trim();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // Dates
                    if (this.containsIgnoreCase(possibleNumericFields, key)) {
                        sql += key + " < '" + sdf.format(new Date(Long.parseLong(value))) + "'";
                    }

                    sql = this.addFilterLogic(sql, search);
                } else {
                    throw new Exception("Invalid filters: " + search.getFilters());
                }
            } else {
                throw new Exception("Invalid filter: " + keyValue);
            }
        }

        if (filters.size() > 0) {
            if (search.getFilterLogic().equals(Search.FILTER_LOGIC_AND)) {
                sql = sql.substring(0, sql.length() - 5);
            } else if (search.getFilterLogic().equals(Search.FILTER_LOGIC_OR)) {
                sql = sql.substring(0, sql.length() - 4);
            }

            sql += ")";
        }

        return sql;
    }

    public String generateFiltersV2(Search search, List<String> possibleNumericFields, String prefix) throws Exception {
        List<String> filters = new ArrayList<>();

        if (search.getFilters().length() > 0) {
            filters = Arrays.asList(search.getFilters().split(";;"));
        }

        String sql = "";

        if (filters.size() > 0) {
            sql += " AND (";
        }

        for (String keyValue : filters) {
            if (StringUtils.contains(keyValue, "::")) {
                String[] split = keyValue.split("::");

                if (split.length == 2) {
                    String key = split[0].trim();
                    String value = split[1].trim();

                    // Strings
                    sql += prefix + "." + key + " = '" + value + "'";

                    sql = this.addFilterLogic(sql, search);
                } else {
                    throw new Exception("Invalid filters: " + search.getFilters());
                }
            } else if (StringUtils.contains(keyValue, ">")) {
                String[] split = keyValue.split(">");

                if (split.length == 2) {
                    String key = split[0].trim();
                    String value = split[1].trim();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // Dates
                    if (this.containsIgnoreCase(possibleNumericFields, key)) {
                        sql += prefix + "." + key + " > '" + sdf.format(new Date(Long.parseLong(value))) + "'";
                    }

                    sql = this.addFilterLogic(sql, search);
                } else {
                    throw new Exception("Invalid filters: " + search.getFilters());
                }
            } else if (StringUtils.contains(keyValue, "<")) {
                String[] split = keyValue.split("<");

                if (split.length == 2) {
                    String key = split[0].trim();
                    String value = split[1].trim();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    // Dates
                    if (this.containsIgnoreCase(possibleNumericFields, key)) {
                        sql += prefix + "." + key + " < '" + sdf.format(new Date(Long.parseLong(value))) + "'";
                    }

                    sql = this.addFilterLogic(sql, search);
                } else {
                    throw new Exception("Invalid filters: " + search.getFilters());
                }
            } else {
                throw new Exception("Invalid filter: " + keyValue);
            }
        }

        if (filters.size() > 0) {
            if (search.getFilterLogic().equals(Search.FILTER_LOGIC_AND)) {
                sql = sql.substring(0, sql.length() - 5);
            } else if (search.getFilterLogic().equals(Search.FILTER_LOGIC_OR)) {
                sql = sql.substring(0, sql.length() - 4);
            }

            sql += ")";
        }

        return sql;
    }

    public String addFilterLogic(String sql, Search search) throws Exception {
        if (search.getFilterLogic().equals(Search.FILTER_LOGIC_AND)) {
            sql += " AND ";
        } else if (search.getFilterLogic().equals(Search.FILTER_LOGIC_OR)) {
            sql += " OR ";
        } else {
            throw new Exception("Invalid filter logic: " + search.getFilterLogic());
        }

        return sql;
    }

    public Boolean containsIgnoreCase(List<String> list, String strToFind) {
        for(String s : list) {
            if (StringUtils.equalsIgnoreCase(s, strToFind)) {
                return true;
            }
        }

        return false;
    }
}
