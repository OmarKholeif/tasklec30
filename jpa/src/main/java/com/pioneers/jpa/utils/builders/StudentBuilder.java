package com.pioneers.jpa.utils.builders;

import com.pioneers.jpa.models.entities.Student;
import com.pioneers.jpa.utils.times.TimeHelper;

import static com.pioneers.jpa.utils.IdHelper.randomUuidV4;

public final class StudentBuilder {

    private StudentBuilder() {
        throw new AssertionError("Utility Class");
    }

    public static Student buildRegisteredStudent(
            final int age,
            final String email,
            final String fullName,
            final String hashedPassword
    ) {
        return Student.builder()
                .id(randomUuidV4())
                .fullName(fullName)
                .age(age)
                .email(email)
                .password(hashedPassword)
                .createdAt(TimeHelper.currentTimestamp())
                .build();
    }
}
