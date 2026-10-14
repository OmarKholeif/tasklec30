package com.pioneers.jpa.errors.exceptions;

import com.pioneers.jpa.utils.times.TimeHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CredentialsException extends RuntimeException {
    private final String description;

    private final Timestamp currentTimestamp = TimeHelper.currentTimestamp();

    public static final int CODE = 6000;
    public static final String MESSAGE = "credentialsError";

    public CredentialsException(String description) {
        super(description);

        this.description = description;
    }
}
