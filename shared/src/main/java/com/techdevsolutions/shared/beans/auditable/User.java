package com.techdevsolutions.shared.beans.auditable;

import com.techdevsolutions.shared.beans.ValidationResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public class User extends Auditable implements Serializable, Comparable<User> {
    private String email = "";

    public static ValidationResponse Validate(User i) {
        return User.Validate(i, false);
    }

    public static ValidationResponse Validate(User i, Boolean isNew) {
        if (!Auditable.Validate(i, isNew).getValid()) {
            return Auditable.Validate(i, isNew);
        }

        if (StringUtils.isEmpty(i.getEmail())) {
            return new ValidationResponse(false, "email", "User email is empty");
        }

        return new ValidationResponse(true, "", "");
    }

    public User() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                "} " + super.toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int compareTo(User o) {
        return this.getId().compareTo(o.getId());
    }
}
