package com.techdevsolutions.shared.service;

import com.techdevsolutions.shared.beans.auditable.User;
import com.techdevsolutions.shared.beans.auditable.UserTest;
import com.techdevsolutions.shared.dao.user.UserSQLiteDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import javax.validation.ValidationException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserSerivceTest {
    @Mock
    Environment environment;

    @Mock
    UserSQLiteDao dao;

    @InjectMocks
    UserService userService = new UserService(this.environment, this.dao);

    @Test(expected = IllegalArgumentException.class)
    public void getWithNullId() throws Exception {
        this.userService.get((String) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWithEmptyId() throws Exception {
        String id = "";
        this.userService.get(id);
    }

    @Test
    public void getWithInvalidId() throws Exception {
        String id = "test";
        User item = new User();
        when(this.dao.get(id)).thenReturn(item);
        User itemToFind = this.userService.get(id);
        Assert.assertTrue(itemToFind != null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWithNullId() throws Exception {
        this.userService.delete(null);
    }



    @Test(expected = IllegalArgumentException.class)
    public void deleteWithEmptyId() throws Exception {
        String id = "";
        this.userService.delete(id);
    }

    @Test
    public void deleteWithInvalidId() throws Exception {
        String id = "test";
        User item = new User();
        when(this.dao.get(id)).thenReturn(item);
        this.userService.delete(id);
        Assert.assertTrue(true);
    }



    @Test(expected = ValidationException.class)
    public void createWithInvalidUser() throws Exception {
        User item = new User();
        this.userService.create(item);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithUserAlreadyExists() throws Exception {
        User item = UserTest.GenerateTestUser();

        when(this.dao.get(item.getId())).thenReturn(item);

        this.userService.create(item);
    }

    @Test
    public void createWithValidUser() throws Exception {
        User item = UserTest.GenerateTestUser();

        when(this.dao.create(item)).thenReturn(item);

        User createdItem = this.userService.create(item);
        Assert.assertTrue(createdItem != null);
    }
}
