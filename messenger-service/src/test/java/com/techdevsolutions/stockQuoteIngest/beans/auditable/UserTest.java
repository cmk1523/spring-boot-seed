package com.techdevsolutions.files.beans.auditable;

import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.beans.auditable.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class UserTest {
    public static User GenerateTestUser() {
        User item = new User();
        item.setId("1");
        item.setName("test user");
        item.setEmail("testuser@gmail.com");
        item.setCreatedBy("test user");
        item.setUpdatedBy(item.getCreatedBy());
        item.setCreatedDate(new Date());
        item.setUpdatedDate(item.getCreatedDate());
        return item;
    }
}
