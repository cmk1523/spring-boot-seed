package com.techdevsolutions.shared.service;

import com.techdevsolutions.shared.beans.Filter;
import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.beans.auditable.User;
import com.techdevsolutions.shared.dao.user.UserSQLiteDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService extends BaseSQLService implements CrudServiceInterface<User> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserSQLiteDao dao;

    @Autowired
    public UserService(Environment environment, UserSQLiteDao dao) {
        super(environment);
        this.dao = dao;
    }

    @Override
    public List<User> search(Filter search) throws Exception {
        return this.dao.search(search);
    }

    @Override
    public List<User> getAll() throws Exception {
        return new ArrayList<>();
    }

    @Override
    public User get(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Unable to get. ID is null or empty.");
        }

        User item = this.dao.get(id);

        if (item != null) {
            return item;
        } else {
            throw new NoSuchElementException("Unable to get. User not found by ID: " + id);
        }
    }

    @Override
    public User create(User item) throws Exception {
        ValidationResponse validationResponse = User.Validate(item, true);

        if (!validationResponse.getValid()) {
            throw new ValidationException(validationResponse.getMessage());
        }

        if (StringUtils.isNotEmpty(item.getId())) {
            try {
                this.get(item.getId());
                throw new IllegalArgumentException("Unable to createDocument. User already exists with ID: " + item.getId());
            } catch (NoSuchElementException e) {
                return this.dao.create(item);
            }
        } else {
            return this.dao.create(item);
        }
    }

    @Override
    public void remove(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Unable to remove. ID is null or empty.");
        }

        try {
            User item = this.get(id); // will throw NoSuchElementException if not found
            item.setRemoved(true);
            this.dao.update(item);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Unable to remove. User not found by ID: " + id);
        }
    }

    @Override
    public void delete(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Unable to delete. ID is null or empty.");
        }

        try {
            this.get(id); // will throw NoSuchElementException if not found
            this.dao.delete(id);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Unable to delete. User not found by ID: " + id);
        }
    }

    @Override
    public User update(User item) throws Exception {
        ValidationResponse validationResponse = User.Validate(item);

        if (!validationResponse.getValid()) {
            throw new ValidationException(validationResponse.getMessage());
        }

        try {
            this.get(item.getId()); // will throw NoSuchElementException if not found
            return this.dao.update(item);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Unable to update. User not found by ID: " + item.getId());
        }
    }

    @Override
    public void install() throws Exception {
        this.dao.dropTable();
        this.dao.createTable();
    }

    public List<User> test() throws Exception {
        this.dao.dropTable();
        this.dao.createTable();
        List<User> list = this.dao.search(null);

        list.forEach((i)->this.logger.info(i.toString()));

        return list;
    }
}
