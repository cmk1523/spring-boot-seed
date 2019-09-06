package com.techdevsolutions.files.service;

import com.techdevsolutions.files.beans.auditable.Thread;
import com.techdevsolutions.files.dao.thread.ThreadFakeDao;
import com.techdevsolutions.shared.beans.Filter;
import com.techdevsolutions.files.beans.auditable.Thread;
import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.service.BaseSQLService;
import com.techdevsolutions.shared.service.CrudServiceInterface;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ThreadService extends BaseSQLService implements CrudServiceInterface<Thread> {
    //    private FileSQLiteDao dao;
    private ThreadFakeDao dao;

    @Autowired
    public ThreadService(Environment environment, ThreadFakeDao dao) {
        super(environment);
        this.dao = dao;
    }

    @Override
    public List<Thread> search(Filter filter) throws Exception {
        return this.dao.search(filter);
    }

    @Override
    public List<Thread> getAll() throws Exception {
        return new ArrayList<>();
    }

    @Override
    public Thread get(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Unable to get. ID is null or empty.");
        }

        Thread item = this.dao.get(id);

        if (item != null) {
            return item;
        } else {
            throw new NoSuchElementException("Unable to get. Thread not found by ID: " + id);
        }
    }

    @Override
    public Thread create(Thread item) throws Exception {
        ValidationResponse validationResponse = Thread.Validate(item, true);

        if (!validationResponse.getValid()) {
            throw new ValidationException(validationResponse.getMessage());
        }

        if (StringUtils.isNotEmpty(item.getId())) {
            try {
                this.get(item.getId());
                throw new IllegalArgumentException("Unable to createDocument. Thread already exists with ID: " + item.getId());
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
            Thread item = this.get(id); // will throw NoSuchElementException if not found
            item.setRemoved(true);
            this.dao.update(item);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Unable to remove. Thread not found by ID: " + id);
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
            throw new IllegalArgumentException("Unable to delete. Thread not found by ID: " + id);
        }
    }

    @Override
    public Thread update(Thread item) throws Exception {
        ValidationResponse validationResponse = Thread.Validate(item);

        if (!validationResponse.getValid()) {
            throw new ValidationException(validationResponse.getMessage());
        }

        try {
            this.get(item.getId()); // will throw NoSuchElementException if not found
            return this.dao.update(item);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Unable to update. Thread not found by ID: " + item.getId());
        }
    }

    @Override
    public void install() throws Exception {

    }


}
