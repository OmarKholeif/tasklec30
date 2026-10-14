package com.pioneers.jpa.errors.exceptions;

import com.pioneers.jpa.utils.times.TimeHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@EqualsAndHashCode(callSuper = false)
public class LoginException extends RuntimeException {
    private final String description;

    private final Timestamp currentTimestamp = TimeHelper.currentTimestamp();

    public static final int CODE = 5000;
    public static final String MESSAGE = "loginError";

    public LoginException(String description) {
        super(description);

        this.description = description;
    }
}
