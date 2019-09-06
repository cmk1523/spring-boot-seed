package com.techdevsolutions.files.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import javax.validation.ValidationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SystemFileSerivceTest {
    @Mock
    Environment environment;

    @InjectMocks
    SystemFileService systemFileService = new SystemFileService(this.environment);

    @Test
    public void getFilesFromDirectory() throws Exception {
        String directory = "/Users/chris/spring-boot-tools-files";

//        when(this.dao.createDocument(item)).thenReturn(item);

        List<File> items = new ArrayList<>();

        try {
            items = this.systemFileService.getFilesFromDirectory(directory, true);
        } catch (IOException e) {
            this.systemFileService.createDirectory(directory);
            items = this.systemFileService.getFilesFromDirectory(directory, true);
        }

        System.out.println(items);
    }
}
