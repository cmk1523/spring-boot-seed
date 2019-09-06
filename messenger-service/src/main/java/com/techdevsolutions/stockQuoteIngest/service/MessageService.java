package com.techdevsolutions.files.service;

import com.techdevsolutions.files.dao.message.MessageFakeDao;
import com.techdevsolutions.shared.beans.Filter;
import com.techdevsolutions.files.beans.auditable.Message;
import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.service.BaseSQLService;
import com.techdevsolutions.shared.service.CrudServiceInterface;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MessageService extends BaseSQLService implements CrudServiceInterface<Message> {
    protected AESEncryptionService encryptionService;

    // private FileSQLiteDao dao;
    private MessageFakeDao dao;

    @Autowired
    public MessageService(Environment environment, AESEncryptionService aesEncryptionService, MessageFakeDao dao) {
        super(environment);
        this.encryptionService = aesEncryptionService;
        this.dao = dao;
    }

    @Override
    public List<Message> search(Filter filter) throws Exception {
        return this.dao.search(filter);
    }

    @Override
    public List<Message> getAll() throws Exception {
        return new ArrayList<>();
    }

    @Override
    public Message get(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Unable to get. ID is null or empty.");
        }

        Message item = this.dao.get(id);

        if (item != null) {
            return item;
        } else {
            throw new NoSuchElementException("Unable to get. Message not found by ID: " + id);
        }
    }

    @Override
    public Message create(Message item) throws Exception {
        ValidationResponse validationResponse = Message.Validate(item, true);

        if (!validationResponse.getValid()) {
            throw new ValidationException(validationResponse.getMessage());
        }

        if (StringUtils.isNotEmpty(item.getId())) {
            try {
                this.get(item.getId());
                throw new IllegalArgumentException("Unable to createDocument. Message already exists with ID: " + item.getId());
            } catch (NoSuchElementException e) {
                return this.dao.create(item);
            }
        } else {
            return this.dao.create(item);
        }
    }

    @Override
    public void remove(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Unable to remove. ID is null or empty.");
        }

        try {
            Message item = this.get(id); // will throw NoSuchElementException if not found
            item.setRemoved(true);
            this.dao.update(item);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Unable to remove. Message not found by ID: " + id);
        }
    }

    @Override
    public void delete(String id) throws Exception {
        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException("Unable to delete. ID is null or empty.");
        }

        try {
            this.get(id); // will throw NoSuchElementException if not found
            this.dao.delete(id);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Unable to delete. Message not found by ID: " + id);
        }
    }

    @Override
    public Message update(Message item) throws Exception {
        ValidationResponse validationResponse = Message.Validate(item);

        if (!validationResponse.getValid()) {
            throw new ValidationException(validationResponse.getMessage());
        }

        try {
            this.get(item.getId()); // will throw NoSuchElementException if not found
            return this.dao.update(item);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Unable to update. Message not found by ID: " + item.getId());
        }
    }

    @Override
    public void install() throws Exception {

    }

    public byte[] generateRandomBytes() {
        return this.encryptionService.generateRandomBytes();
    }

    public Message encryptMessage(final Message message, final String key) throws Exception {
        byte[] iv = this.generateRandomBytes();
        byte[] salt = this.generateRandomBytes();
        return this.encryptMessage(message, key, iv, salt);
    }

    public Message encryptMessage(final Message message, final String key, final byte[] iv, final byte[] salt) throws Exception {
        String newText = this.encryptionService.encrypt(message.getText(), key, iv, salt);
        Message newMessage = SerializationUtils.clone(message);
        newMessage.setText(newText);
        return newMessage;
    }

    public Message decryptMessage(final Message message, final String key) throws Exception {
        String newText = this.encryptionService.decrypt(message.getText(), key);
        Message newMessage = SerializationUtils.clone(message);
        newMessage.setText(newText);
        return newMessage;
    }
}
