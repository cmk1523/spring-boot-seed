package com.techdevsolutions.files.beans.auditable;

import com.techdevsolutions.shared.beans.ValidationResponse;
import com.techdevsolutions.shared.beans.auditable.Auditable;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public class Message extends Auditable implements Serializable, Comparable<Message> {
    private String userId = "";
    private String text = "";

    public Message() {
        super();
    }

    public static ValidationResponse Validate(Message i) {
        return Message.Validate(i, false);
    }

    public static ValidationResponse Validate(Message i, Boolean isNew) {
        if (!Auditable.Validate(i, isNew).getValid()) {
            return Auditable.Validate(i, isNew);
        }

        if (StringUtils.isEmpty(i.getUserId())) {
            return new ValidationResponse(false, "userId", "Message userId is empty");
        }

        if (StringUtils.isEmpty(i.getText())) {
            return new ValidationResponse(false, "text", "Message text is empty");
        }

        return new ValidationResponse(true, "", "");
    }

    @Override
    public String toString() {
        return "Message{" +
                "userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        if (!super.equals(o)) return false;
        Message message = (Message) o;
        return Objects.equals(userId, message.userId) &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, text);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(Message o) {
        return this.getId().compareTo(o.getId());
    }
}
