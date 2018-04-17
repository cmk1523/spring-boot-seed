package com.techdevsolutions.dao.test.user;

import com.techdevsolutions.beans.Filter;
import com.techdevsolutions.beans.Search;
import com.techdevsolutions.beans.auditable.User;
import com.techdevsolutions.dao.DaoCrudInterface;
import com.techdevsolutions.dao.test.BaseTestDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserTestDao extends BaseTestDao implements DaoCrudInterface<User> {
    private Logger logger = Logger.getLogger(UserTestDao.class.getName());
    private static List<User> Users = new ArrayList<>();

    @Autowired
    public UserTestDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);

        User i = new User();
        i.setId(1);
        i.setName("test name");
        i.setCreatedBy("test");
        i.setCreatedDate(new Date().getTime());
        i.setUpdatedBy(i.getCreatedBy());
        i.setUpdatedDate(i.getCreatedDate());
        UserTestDao.Users.add(i);
    }


    public List<User> search(Search search) throws Exception {
        logger.info("UserTestDao - search");
        List<User> list = UserTestDao.Users.stream()
                .filter(item -> item.toString().indexOf(search.getTerm()) >= 0)
                .collect(Collectors.toList());
        return this.filter(search, list);
    }

    public List<User> get(Filter filter) throws Exception {
        logger.info("UserTestDao - get");
        List<User> list = UserTestDao.Users.stream().filter(item -> !item.getRemoved()).collect(Collectors.toList());
        return this.filter(filter, list);
    }

    public User get(Integer id) {
        logger.info("UserMySqlDao - get - id: " + id);
        for (User item : UserTestDao.Users) {
            if (item.getId().equals(id) && !item.getRemoved()) {
                return item;
            }
        }

        return null;
    }

    public void remove(Integer id) throws Exception {
        logger.info("UserMySqlDao - remove - id: " + id);
        User user = this.get(id);

        if (user != null) {
            user.setRemoved(true);
        } else {
            throw new Exception("Unable to find item by id: " + id);
        }
    }

    public void delete(Integer id) throws Exception {
        logger.info("UserMySqlDao - delete - id: " + id);
        User user = this.get(id);

        if (user != null) {
            UserTestDao.Users = UserTestDao.Users.stream().filter(item -> !item.getId().equals(id)).collect(Collectors.toList());
        } else {
            throw new Exception("Unable to find item by id: " + id);
        }
    }

    public User create(User item) throws Exception {
        logger.info("UserMySqlDao - create - id: " + item.getId());
        item.setId(UserTestDao.Users.size() + 1);
        UserTestDao.Users.add(item);
        return item;
    }

    @Override
    public User update(User newItem) throws Exception {
        logger.info("UserMySqlDao - update - id: " + newItem.getId());
        User item = this.get(newItem.getId());

        if (item != null) {
            item.setName(newItem.getName());
            item.setUpdatedBy(newItem.getUpdatedBy());
            item.setUpdatedDate(newItem.getUpdatedDate());
            return item;
        } else {
            throw new Exception("Unable to find item by id: " + newItem.getId());
        }
    }

    private List<User> filter(Filter filter, List<User> list) throws Exception {
        // Filter...
        if (StringUtils.isNotEmpty(filter.getFilters()) && StringUtils.isNotEmpty(filter.getFilterLogic())) {
            String[] split = filter.getFilters().split("::");

            if (split.length == 2) {
                String key = split[0];
                String value = split[1];

                if (filter.getFilterLogic().equals(Filter.FILTER_LOGIC_AND)) {
                    list = ("id".equals(key)) ? UserTestDao.Users.stream().filter(item -> item.getId()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("name".equals(key)) ? UserTestDao.Users.stream().filter(item -> item.getName()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("createdBy".equals(key)) ? UserTestDao.Users.stream().filter(item -> item.getCreatedBy()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("createdDate".equals(key)) ? UserTestDao.Users.stream().filter(item -> item.getCreatedDate()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("updatedBy".equals(key)) ? UserTestDao.Users.stream().filter(item -> item.getUpdatedBy()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("updatedDate".equals(key)) ? UserTestDao.Users.stream().filter(item -> item.getUpdatedDate()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("removed".equals(key)) ? UserTestDao.Users.stream().filter(item -> item.getRemoved()
                            .equals(value)).collect(Collectors.toList()) : list;
                } else if (filter.getFilterLogic().equals(Filter.FILTER_LOGIC_NOT)) {
                    list = ("id".equals(key)) ? UserTestDao.Users.stream().filter(item -> !item.getId()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("name".equals(key)) ? UserTestDao.Users.stream().filter(item -> !item.getName()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("createdBy".equals(key)) ? UserTestDao.Users.stream().filter(item -> !item.getCreatedBy()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("createdDate".equals(key)) ? UserTestDao.Users.stream().filter(item -> !item.getCreatedDate()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("updatedBy".equals(key)) ? UserTestDao.Users.stream().filter(item -> !item.getUpdatedBy()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("updatedDate".equals(key)) ? UserTestDao.Users.stream().filter(item -> !item.getUpdatedDate()
                            .equals(value)).collect(Collectors.toList()) : list;
                    list = ("removed".equals(key)) ? UserTestDao.Users.stream().filter(item -> !item.getRemoved()
                            .equals(value)).collect(Collectors.toList()) : list;
                } else if (filter.getFilterLogic().equals(Filter.FILTER_LOGIC_OR)) {
                    // TODO: OR logic...
                    throw new Exception("OR Logic not implemented!");
                }
            } else {
                throw new Exception("Invalid filter syntax: " + filter.getFilters());
            }
        }

        // Sort...
        if (StringUtils.isNotEmpty(filter.getSort()) && StringUtils.isNotEmpty(filter.getOrder())) {
            Comparator<User> comparator = null;
            comparator = ("id".equals(filter.getSort())) ? Comparator.comparing(User::getId) : comparator;
            comparator = ("name".equals(filter.getSort())) ? Comparator.comparing(User::getName) : comparator;
            comparator = ("createdBy".equals(filter.getSort())) ? Comparator.comparing(User::getCreatedBy) : comparator;
            comparator = ("createdDate".equals(filter.getSort())) ? Comparator.comparing(User::getCreatedDate) : comparator;
            comparator = ("updatedBy".equals(filter.getSort())) ? Comparator.comparing(User::getUpdatedBy) : comparator;
            comparator = ("updatedDate".equals(filter.getSort())) ? Comparator.comparing(User::getUpdatedDate) : comparator;
            comparator = ("removed".equals(filter.getSort())) ? Comparator.comparing(User::getRemoved) : comparator;

            if (comparator == null) {
                throw new Exception("Unable sort field: " + filter.getSort());
            }

            Collections.sort(list, comparator);

            if (filter.getOrder().equals(Filter.SORT_DESC)) {
                Collections.sort(list, comparator.reversed());
            }
        }

        // Size & Pagination...
        Integer startPos = filter.getSize() * filter.getPage();
        startPos = startPos < 0 ? 0 : startPos;

        if (startPos > list.size() - 1) {
            this.logger.info("Invalid start position: " + startPos);
            return new ArrayList<>();
        }

        Integer endPos = startPos + filter.getSize();
        endPos = endPos > list.size() ? list.size() : endPos;
        list = list.subList(startPos, endPos);
        return list;
    }
}
