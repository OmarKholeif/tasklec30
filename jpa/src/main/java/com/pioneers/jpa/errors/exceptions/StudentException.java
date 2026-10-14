package com.pioneers.jpa.errors.exceptions;

import com.pioneers.jpa.utils.times.TimeHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@EqualsAndHashCode(callSuper = false)
public class StudentException extends RuntimeException {
    private final String description;

    private final Timestamp currentTimestamp = TimeHelper.currentTimestamp();

    public static final int CODE = 1000;
    public static final String MESSAGE = "studentError";

    public StudentException(String description) {
        super(description);

        this.description = description;
    }
}
