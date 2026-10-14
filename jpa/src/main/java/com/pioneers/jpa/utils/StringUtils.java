package com.pioneers.jpa.utils;

public final class StringUtils {

    private StringUtils() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static boolean isNullOrBlank(final String string) {
        return string == null || string.isBlank();
    }
}
