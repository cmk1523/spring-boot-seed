package com.techdevsolutions.files.service;

import com.techdevsolutions.files.beans.auditable.File;
import com.techdevsolutions.files.dao.file.FileFakeDao;
import com.techdevsolutions.shared.beans.Filter;
import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.service.BaseSQLService;
import com.techdevsolutions.shared.service.CrudServiceInterface;
import com.techdevsolutions.shared.service.HashUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FileService extends BaseSQLService implements CrudServiceInterface<File> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

//    private FileSQLiteDao dao;
    private FileFakeDao dao;
    private SystemFileService systemFileService;

    @Autowired
    public FileService(Environment environment, FileFakeDao dao, SystemFileService systemFileService) {
        super(environment);
        this.dao = dao;
        this.systemFileService = systemFileService;
    }

    public void scanAndLoadRootDirectory() throws Exception {
        String userId = "test user id";
        String userName = "test user name";

        this.systemFileService.createRootDirectory();
        String rootDirectory = this.systemFileService.getRootDirectoryName();
        List<java.io.File> rootSystemFiles = this.systemFileService.getFilesFromDirectory(rootDirectory, false);
        List<File> rootFiles = rootSystemFiles.stream().map((i)->{
            File file = new File();
            file.setPath(i.getPath());
            file.setName(i.getName());
            file.setSize(i.length());
            file.setUserId(userId);
            file.setCreatedBy(userName);
            file.setUpdatedBy(file.getCreatedBy());

            try {
                file.setHash(HashUtils.md5(i));
            } catch (Exception e) {
                e.printStackTrace();
            }

            file.setId(file.getHash());

            try {
                file.setContentType(Files.probeContentType(i.toPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            ValidationResponse vr = File.Validate(file);

            if (!vr.getValid()) {
                try {
                    throw new Exception("Invalid file: " + vr.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            return file;
        }).collect(Collectors.toList());

        this.logger.info(rootFiles.toString());
    }

    @Override
    public List<File> search(Filter filter) throws Exception {
        return this.dao.search(filter);
    }

    @Override
    public List<File> getAll() throws Exception {
        return new ArrayList<>();
    }

    @Override
    public File get(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Unable to get. ID is null or empty.");
        }

        File item = this.dao.get(id);

        if (item != null) {
            return item;
        } else {
            throw new NoSuchElementException("Unable to get. File not found by ID: " + id);
        }
    }

    @Override
    public File create(File item) throws Exception {
        ValidationResponse validationResponse = File.Validate(item, true);

        if (!validationResponse.getValid()) {
            throw new ValidationException(validationResponse.getMessage());
        }

        if (StringUtils.isNotEmpty(item.getId())) {
            try {
                this.get(item.getId());
                throw new IllegalArgumentException("Unable to createDocument. File already exists with ID: " + item.getId());
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
            File item = this.get(id); // will throw NoSuchElementException if not found
            item.setRemoved(true);
            this.dao.update(item);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Unable to remove. File not found by ID: " + id);
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
            throw new IllegalArgumentException("Unable to delete. File not found by ID: " + id);
        }
    }

    @Override
    public File update(File item) throws Exception {
        ValidationResponse validationResponse = File.Validate(item);

        if (!validationResponse.getValid()) {
            throw new ValidationException(validationResponse.getMessage());
        }

        try {
            this.get(item.getId()); // will throw NoSuchElementException if not found
            return this.dao.update(item);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Unable to update. File not found by ID: " + item.getId());
        }
    }

    @Override
    public void install() throws Exception {
//        this.dao.dropTable();
//        this.dao.createTable();
    }

    public List<File> test() throws Exception {
//        this.dao.dropTable();
//        this.dao.createTable();
        List<File> list = this.dao.search(new Filter());
        return list;
    }
}
