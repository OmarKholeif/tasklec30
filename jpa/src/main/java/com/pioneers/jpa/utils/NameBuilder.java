package com.pioneers.jpa.utils;

public final class NameBuilder {

    private NameBuilder() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static String buildFullName(final String firstName, final String secondName) {
        return firstName + " " + secondName;
    }
}
