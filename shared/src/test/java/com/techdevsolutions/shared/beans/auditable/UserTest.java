package com.techdevsolutions.shared.beans.auditable;

import com.techdevsolutions.shared.beans.ValidationResponse;
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

    @Test
    public void test() {
        User item = new User();
        ValidationResponse vr = User.Validate(item);
        Assert.assertTrue(!vr.getValid());
    }
}
