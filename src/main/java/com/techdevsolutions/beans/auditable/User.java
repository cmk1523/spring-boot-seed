package com.techdevsolutions.beans.auditable;

import com.techdevsolutions.beans.ValidationResponse;

import java.io.Serializable;

public class User extends Auditable implements Serializable {

    public static ValidationResponse Validate(Auditable i) {
        return User.Validate(i, false);
    }

    public static ValidationResponse Validate(Auditable i, Boolean isNew) {
        if (!Auditable.Validate(i, isNew).getValid()) {
            return Auditable.Validate(i, isNew);
        }

        return new ValidationResponse(true, "", "");
    }

    public User() {
        super();
    }

}
