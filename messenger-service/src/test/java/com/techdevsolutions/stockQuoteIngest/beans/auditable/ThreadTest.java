package com.techdevsolutions.files.beans.auditable;

import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.beans.auditable.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ThreadTest {
    public static Thread GenerateTestThead(Message message) {
        Thread thread = new Thread();
        thread.setId("1");
        thread.setCreatedBy("test user");
        thread.setUpdatedBy(message.getCreatedBy());
        thread.setCreatedDate(new Date());
        thread.setUpdatedDate(message.getCreatedDate());
        thread.setKey("test");

        User user = UserTest.GenerateTestUser();
        user.setId("testUserId");
        thread.getUsers().add(user);

        return thread;
    }

    @Test
    public void test() {
        Message a = new Message();
        ValidationResponse vr = Message.Validate(a);
        Assert.assertTrue(!vr.getValid());
    }
}
