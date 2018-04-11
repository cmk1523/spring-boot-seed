package com.techdevsolutions.dao.test;

import com.techdevsolutions.beans.Search;
import com.techdevsolutions.beans.auditable.User;
import com.techdevsolutions.dao.DaoCrudInterface;
import com.techdevsolutions.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserTestDao implements DaoCrudInterface<User> {
    private Logger logger = Logger.getLogger(UserTestDao.class.getName());
    private static List<User> Users = new ArrayList<>();

    @Autowired
    public UserTestDao() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("test name");
        user1.setCreatedBy("test created user");
        user1.setCreatedDate(new Date().getTime());
        user1.setUpdatedBy(user1.getCreatedBy());
        user1.setUpdatedDate(user1.getCreatedDate());
        UserTestDao.Users.add(user1);
    }

    public List<User> search(Search search) throws Exception {
        List<User> list = UserTestDao.Users.stream().filter(item -> !item.getRemoved()).collect(Collectors.toList());

        // Filter...
        if (StringUtils.isNotEmpty(search.getFilters()) && StringUtils.isNotEmpty(search.getFilterLogic())) {
            String[] split = search.getFilters().split("::");

            if (split.length == 2) {
                String key = split[0];
                String value = split[1];

                if (search.getFilterLogic().equals(Search.FILTER_LOGIC_AND)) {
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
                } else if (search.getFilterLogic().equals(Search.FILTER_LOGIC_NOT)) {
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
                } else if (search.getFilterLogic().equals(Search.FILTER_LOGIC_OR)) {
                    // TODO: OR logic...
                    throw new Exception("OR Logic not implemented!");
                }
            } else {
                throw new Exception("Invalid filter syntax: " + search.getFilters());
            }
        }

        // Sort...
        if (StringUtils.isNotEmpty(search.getSort()) && StringUtils.isNotEmpty(search.getOrder())) {
            Comparator<User> comparator = null;
            comparator = ("id".equals(search.getSort())) ? Comparator.comparing(User::getId) : comparator;
            comparator = ("name".equals(search.getSort())) ? Comparator.comparing(User::getName) : comparator;
            comparator = ("createdBy".equals(search.getSort())) ? Comparator.comparing(User::getCreatedBy) : comparator;
            comparator = ("createdDate".equals(search.getSort())) ? Comparator.comparing(User::getCreatedDate) : comparator;
            comparator = ("updatedBy".equals(search.getSort())) ? Comparator.comparing(User::getUpdatedBy) : comparator;
            comparator = ("updatedDate".equals(search.getSort())) ? Comparator.comparing(User::getUpdatedDate) : comparator;
            comparator = ("removed".equals(search.getSort())) ? Comparator.comparing(User::getRemoved) : comparator;

            if (comparator == null) {
                throw new Exception("Unable sort field: " + search.getSort());
            }

            Collections.sort(list, comparator);

            if (search.getOrder().equals(Search.SORT_DESC)) {
                Collections.sort(list, comparator.reversed());
            }
        }

        // Size & Pagination...
        Integer startPos = search.getSize() * search.getPage();
        startPos = startPos < 0 ? 0 : startPos;

        if (startPos > list.size() - 1) {
            this.logger.info("Invalid start position: " + startPos);
            return new ArrayList<>();
        }

        this.logger.info("startPos: " + startPos);

        Integer endPos = startPos + search.getSize();
        endPos = endPos > list.size() ? list.size() : endPos;

        this.logger.info("endPos: " + endPos);

        list = list.subList(startPos, endPos);
        return list;
    }

    public List<User> getAll() {
        return UserTestDao.Users.stream().filter(item -> !item.getRemoved()).collect(Collectors.toList());
    }

    public User get(Integer id) {
        for (User item : UserTestDao.Users) {
            if (item.getId().equals(id) && !item.getRemoved()) {
                return item;
            }
        }

        return null;
    }

    public void remove(Integer id) throws Exception {
        User user = this.get(id);

        if (user != null) {
            user.setRemoved(true);
        } else {
            throw new Exception("Unable to find item by id: " + id);
        }
    }

    public void delete(Integer id) throws Exception {
        User user = this.get(id);

        if (user != null) {
            UserTestDao.Users = UserTestDao.Users.stream().filter(item -> !item.getId().equals(id)).collect(Collectors.toList());
        } else {
            throw new Exception("Unable to find item by id: " + id);
        }
    }

    public User create(User item) throws Exception {
        item.setId(UserTestDao.Users.size() + 1);

        User user = this.get(item.getId());

        if (user == null) {
            UserTestDao.Users.add(item);
            return item;
        } else {
            throw new Exception("Item already exists: " + item.getId());
        }
    }

    @Override
    public User update(User item) throws Exception {
        User user = this.get(item.getId());

        if (user != null) {
            user.setName(item.getName());
            user.setUpdatedBy(item.getUpdatedBy());
            user.setUpdatedDate(item.getUpdatedDate());
            return item;
        } else {
            throw new Exception("Unable to find item by id: " + item.getId());
        }
    }
}
