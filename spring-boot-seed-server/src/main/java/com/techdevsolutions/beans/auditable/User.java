package com.techdevsolutions.beans.auditable;

import com.techdevsolutions.beans.ValidationResponse;

import java.io.Serializable;

public class User extends Auditable implements Serializable {

    public static ValidationResponse Validate(Auditable i) {
        if (!Auditable.Validate(i).getValid()) {
            return Auditable.Validate(i);
        }

        return new ValidationResponse(true, "", "");
    }

    public User() {
        super();
    }

}
