package com.techdevsolutions.files.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SystemFileService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Environment environment;

    @Autowired
    public SystemFileService(Environment environment) {
        this.environment = environment;
    }

    public String getRootDirectoryName() {
        return this.environment.getProperty("files.root.directory");
    }

    public List<File> getFilesFromDirectory(String directoryName, Boolean includeDirectories) throws Exception {
        File directory = new File(directoryName);

        if (!directory.isDirectory()) {
            throw new IOException(directoryName + " does not exist as a directory");
        }

        File[] filesArray = directory.listFiles();

        if (filesArray != null) {
            List<File> files = new ArrayList<>(Arrays.asList(filesArray));

            if (includeDirectories) {
                return files;
            } else {
                return files.stream().filter((i)->!i.isDirectory()).collect(Collectors.toList());
            }
        } else {
            return new ArrayList<>();
        }
    }

    public void createDirectory(String directoryName) throws IOException {
        Path path = Paths.get(directoryName);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    public void createRootDirectory() throws Exception {
        String rootDirectory = this.getRootDirectoryName();

        if (StringUtils.isEmpty(rootDirectory)) {
            throw new Exception("yaml setting not found: files.root.directory");
        }

        this.createDirectory(rootDirectory);
    }

}
