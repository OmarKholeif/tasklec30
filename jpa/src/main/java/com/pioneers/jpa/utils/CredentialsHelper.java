package com.pioneers.jpa.utils;

import com.pioneers.jpa.errors.exceptions.CredentialsException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

public final class CredentialsHelper {

    private CredentialsHelper() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static String hashPassword(final String password) throws CredentialsException {
        Optional.ofNullable(password)
                .orElseThrow(() -> new CredentialsException("Password cannot be null"));

        byte[] hash;
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            hash = md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new CredentialsException("SHA-256 algorithm not found");
        }

        return Base64.getEncoder().encodeToString(hash);
    }
}
