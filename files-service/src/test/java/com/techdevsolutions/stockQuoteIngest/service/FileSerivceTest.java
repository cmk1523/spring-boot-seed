package com.techdevsolutions.files.service;

import com.techdevsolutions.files.beans.auditable.File;
import com.techdevsolutions.files.beans.auditable.FileTest;
import com.techdevsolutions.files.dao.file.FileFakeDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import javax.validation.ValidationException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileSerivceTest {
    @Mock
    Environment environment;

//    @Mock
//    FileSQLiteDao dao;

    @Mock
    FileFakeDao dao;

    @Mock
    SystemFileService systemFileService;

    @InjectMocks
    FileService fileService = new FileService(this.environment, this.dao, this.systemFileService);

    @Test(expected = IllegalArgumentException.class)
    public void getWithNullId() throws Exception {
        this.fileService.get((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWithEmptyId() throws Exception {
        String id = "";
        this.fileService.get(id);
    }

    @Test
    public void getWithInvalidId() throws Exception {
        String id = "test";
        File item = new File();
        when(this.dao.get(id)).thenReturn(item);
        File itemToFind = this.fileService.get(id);
        Assert.assertTrue(itemToFind != null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWithNullId() throws Exception {
        this.fileService.delete(null);
    }



    @Test(expected = IllegalArgumentException.class)
    public void deleteWithEmptyId() throws Exception {
        String id = "";
        this.fileService.delete(id);
    }

    @Test
    public void deleteWithInvalidId() throws Exception {
        String id = "test";
        File item = new File();
        when(this.dao.get(id)).thenReturn(item);
        this.fileService.delete(id);
        Assert.assertTrue(true);
    }



    @Test(expected = ValidationException.class)
    public void createWithInvalidFile() throws Exception {
        File item = new File();
        this.fileService.create(item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithFileAlreadyExists() throws Exception {
        File item = FileTest.GenerateTestFile();

        when(this.dao.get(item.getId())).thenReturn(item);

        this.fileService.create(item);
    }

    @Test
    public void createWithValidFile() throws Exception {
        File item = FileTest.GenerateTestFile();

        when(this.dao.create(item)).thenReturn(item);

        File createdItem = this.fileService.create(item);
        Assert.assertTrue(createdItem != null);
    }
}
