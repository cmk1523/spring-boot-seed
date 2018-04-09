package com.techdevsolutions.dao.test;

import com.techdevsolutions.beans.auditable.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTestDao implements DaoTestCrudInterface<User> {
    private static List<User> Users = new ArrayList<>();

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

    public List<User> getAll() {
        return UserTestDao.Users;
    }

    public User get(Integer id) {
        for (User item : UserTestDao.Users) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
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
