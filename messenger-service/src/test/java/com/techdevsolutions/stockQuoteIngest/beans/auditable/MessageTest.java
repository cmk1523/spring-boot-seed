package com.techdevsolutions.files.beans.auditable;

import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.beans.auditable.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class MessageTest {
    public static Message GenerateTestMessage(User user) {
        Message message = new Message();
        message.setId("1");
        message.setCreatedBy("test user");
        message.setUpdatedBy(message.getCreatedBy());
        message.setCreatedDate(new Date());
        message.setUpdatedDate(message.getCreatedDate());
        message.setUserId("testUserId");
        message.setText("hello world");
        return message;
    }

    @Test
    public void test() {
        Message a = new Message();
        ValidationResponse vr = Message.Validate(a);
        Assert.assertTrue(!vr.getValid());
    }
}
