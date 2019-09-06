package com.techdevsolutions.files.beans.auditable;

import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.beans.auditable.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class FileTest {
    public static File GenerateTestFile() {
        User user = UserTest.GenerateTestUser();
        File item = new File();
        item.setId("1");
        item.setName("test file name");
        item.setCreatedBy("test user");
        item.setUpdatedBy(item.getCreatedBy());
        item.setCreatedDate(new Date());
        item.setUpdatedDate(item.getCreatedDate());
        item.setUserId(user.getId());
        item.setPath("test/path");
        item.setSize(100L);
        item.setHash("1234567890");
        item.setHash("text/plain");
        return item;
    }

    @Test
    public void test() {
        File item = new File();
        ValidationResponse vr = File.Validate(item);
        Assert.assertTrue(!vr.getValid());
    }
}
