package com.techdevsolutions.files.service;

import com.techdevsolutions.files.beans.auditable.*;
import com.techdevsolutions.files.beans.auditable.Thread;
import com.techdevsolutions.files.dao.message.MessageFakeDao;
import com.techdevsolutions.shared.beans.auditable.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.env.Environment;

public class MessageServiceTest {
    AESEncryptionService encryptionService = new AESEncryptionService();
    Environment environment = null;
    MessageFakeDao messageFakeDao = new MessageFakeDao();
    MessageService messageService = new MessageService(this.environment, this.encryptionService, this.messageFakeDao);

    @Test
    public void test() throws Exception {
        User user = UserTest.GenerateTestUser();
        Message message = MessageTest.GenerateTestMessage(user);
        Thread thread = ThreadTest.GenerateTestThead(message);

        message = this.messageService.encryptMessage(message, thread.getKey());
        Assert.assertTrue(message.getText().length() > 0);

        thread.getMessages().add(message);

        message = this.messageService.decryptMessage(message, thread.getKey());
        Assert.assertTrue(message.getText().equals("hello world"));
    }

    @Test
    public void test2() throws Exception {
        User user = UserTest.GenerateTestUser();
        Message message = MessageTest.GenerateTestMessage(user);
        String originalMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus vel nisi eu purus egestas " +
                "consectetur sed at nunc. Vestibulum sed nibh nec nisl luctus egestas volutpat at ipsum. In hac " +
                "abitasse platea dictumst. Maecenas volutpat scelerisque sem eu consequat. Donec vitae augue consequat, " +
                "fermentum arcu vel, facilisis erat. Vivamus congue fermentum arcu. Nulla varius blandit felis, aliquet " +
                "pharetra enim scelerisque non. Nam commodo mi nec maximus sodales.";
        message.setText(originalMessage);
        Thread thread = ThreadTest.GenerateTestThead(message);

        // The following is just to reproduce the same encrypted message for a test. Don't use a static iv nor salt!
        byte[] iv = new byte[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        Assert.assertTrue(iv.length == AESEncryptionService.IV_SIZE);
        byte[] salt = new byte[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        Assert.assertTrue(salt.length == AESEncryptionService.SALT_SIZE);
        message = this.messageService.encryptMessage(message, thread.getKey(), iv, salt);
        String encryptedMessage = "AAECAwQFBgcICQoLDA0ODw==AQEBAQEBAQEBAQEBAQEBAQ==jJmIb1afrShdxKvcBp+lHRA6txFmbzBujmuyN" +
                "uPxn7aEVlNgBAOA1nObJNSxhLGCEbzwslJXWeMhlWwG/iKTyl61XFnzGyW0irlrwlavbJPA7aE8WuEVOvjTEP9DI/3Vn6MIoYeS3LBE" +
                "t06FYaHjDaLNAhzuLmo8GdAMVA9NQV2OlgtKebIVG1xkaS/cD9IF2cF9o1rzuw5sjWiJIwrFRK117C0BN6f+Cx4R8iIoOw/7N6ul+sp" +
                "yR+5ken2MfTI5rkU5WDBFiWpGkv1XhHOXsSb/ozUBvUrFU5Gf+e2NnJRjU44+Oh37fQKS64Uetl9cpi0xu+zuznDe3a4iBXT/XW8sec" +
                "VgYKcueaWfSWdolrT0yvoBk5dG8wkkBHzllXxhmz5bLaY4vtfZ66fRhhi9YB+ClDPRCk067ndf3XcFgEiz2I8MgQRzNw3sfVOGD93zl" +
                "BsdOB1FMG3TRkIRwMVSQXP8WEm7n6xc6DwIOsyOxeLUcJiK7/huPceb+JIXRbN9dNUNSws2VZlcrVGUohKiyUhCyN47hNR6ARKO26s/" +
                "fgr587FltkzVeHkaabYQFWhoQ1VQXMbFHdNHON5sBX61z07rFj+jilQgg6Tkl6I4wdw=";
        Assert.assertTrue(message.getText().equals(encryptedMessage));

        message = this.messageService.decryptMessage(message, thread.getKey());
        Assert.assertTrue(message.getText().equals(originalMessage));


        iv = this.messageService.generateRandomBytes();
        Assert.assertTrue(iv.length == AESEncryptionService.IV_SIZE);
        salt = this.messageService.generateRandomBytes();
        Assert.assertTrue(salt.length == AESEncryptionService.SALT_SIZE);
        message = this.messageService.encryptMessage(message, thread.getKey(), iv, salt);

        message = this.messageService.decryptMessage(message, thread.getKey());
        Assert.assertTrue(message.getText().equals(originalMessage));
    }
}
