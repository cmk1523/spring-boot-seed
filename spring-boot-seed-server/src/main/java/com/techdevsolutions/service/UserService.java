package com.techdevsolutions.service;

import com.techdevsolutions.beans.BaseItem;
import com.techdevsolutions.beans.User;
import com.techdevsolutions.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Service
public class UserService implements BasicServiceInterface<User> {
    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    UserDao userDao;

    public List<User> getAll() throws Exception {
        logger.info("UserService - getAll");
        return this.userDao.getAll();
    }

    public User get(User user) throws Exception {
        logger.info("UserService - delete - ID: " + user.getId());
        User item = this.userDao.get(user.getId());

        if (item == null) {
            throw new NoSuchElementException("UserService - get - Item was not found using ID: " + user.getId());
        }

        return item;
    }

    public User get(Integer id) throws Exception {
        logger.info("UserService - get - ID: " + id);
        return this.userDao.get(id);
    }

    public User create(User item) throws Exception {
        logger.info("UserService - create - ID: " + item.getId());
        BaseItem i = this.get(item.getId());

        if (i != null) {
            throw new IllegalArgumentException("UserService - create - Item already exists with ID: " + item.getId());
        }

        return this.userDao.create(item);
    }

    public void delete(Integer id) throws Exception {
        logger.info("UserService - delete - ID: " + id);
        User i = this.get(id);

        if (i == null) {
            throw new NoSuchElementException("UserService - delete - Item was not found using ID: "+ id);
        }

        this.userDao.delete(id);
    }

    public User update(User item) throws Exception {
        logger.info("UserService - update - ID: " + item.getId());
        User i = this.get(item.getId());

        if (i == null) {
            throw new NoSuchElementException("UserService - update - Item was not found using ID: " + item.getId());
        }

        i.setName(item.getName());
        i.setUpdatedBy(item.getUpdatedBy());
        i.setUpdatedDate(item.getUpdatedDate());
        return this.update(i);

        // OR
        // return this.update(item);
    }
}
