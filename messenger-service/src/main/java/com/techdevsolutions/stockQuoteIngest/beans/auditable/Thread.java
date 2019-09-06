package com.techdevsolutions.files.beans.auditable;

import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.beans.auditable.Auditable;
import com.techdevsolutions.shared.beans.auditable.User;

import java.io.Serializable;
import java.util.*;

public class Thread extends Auditable implements Serializable, Comparable<Thread> {
    private String key = "";

    private TreeSet<User> users = new TreeSet<>(new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.compareTo(o2);
        }
    });

    private TreeSet<Message> messages = new TreeSet<>(new Comparator<Message>() {
        @Override
        public int compare(Message o1, Message o2) {
            return o1.compareTo(o2);
        }
    });

    public static ValidationResponse Validate(Thread i) {
        return Thread.Validate(i, false);
    }

    public static ValidationResponse Validate(Thread i, Boolean isNew) {
        if (!Auditable.Validate(i, isNew).getValid()) {
            return Auditable.Validate(i, isNew);
        }

        if (i.getUsers() == null) {
            return new ValidationResponse(false, "users", "Thread users is null");
        }

        if (i.getMessages() == null) {
            return new ValidationResponse(false, "messages", "Thread messages is null");
        }

        return new ValidationResponse(true, "", "");
    }

    public Thread() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Thread)) return false;
        if (!super.equals(o)) return false;
        Thread thread = (Thread) o;
        return Objects.equals(users, thread.users) &&
                Objects.equals(messages, thread.messages) &&
                Objects.equals(key, thread.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), users, messages, key);
    }

    @Override
    public String toString() {
        return "Thread{" +
                "users=" + users +
                ", messages=" + messages +
                ", key='" + key + '\'' +
                "} " + super.toString();
    }

    public TreeSet<User> getUsers() {
        return users;
    }

    public void setUsers(TreeSet<User> users) {
        this.users = users;
    }

    public TreeSet<Message> getMessages() {
        return messages;
    }

    public void setMessages(TreeSet<Message> messages) {
        this.messages = messages;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int compareTo(Thread o) {
        return this.getId().compareTo(o.getId());
    }
}
