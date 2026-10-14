package com.pioneers.jpa.errors.handlers;

import com.pioneers.jpa.errors.exceptions.CredentialsException;
import com.pioneers.jpa.errors.exceptions.LoginException;
import com.pioneers.jpa.errors.exceptions.LogoutException;
import com.pioneers.jpa.errors.exceptions.RegisterException;
import com.pioneers.jpa.errors.models.ErrorResponse;
import com.pioneers.jpa.errors.models.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthStudentHandler {

    @ExceptionHandler(exception = RegisterException.class)
    public GenericResponse<?> handleRegisterException(final RegisterException e) {
        return new GenericResponse<>(
                RegisterException.CODE,
                e.getCurrentTimestamp(),
                new ErrorResponse(RegisterException.MESSAGE, e.getDescription())
        );
    }

    @ExceptionHandler(exception = LogoutException.class)
    public GenericResponse<?> handleLogoutException(final LogoutException e) {
        return new GenericResponse<>(
                LogoutException.CODE,
                e.getCurrentTimestamp(),
                new ErrorResponse(LogoutException.MESSAGE, e.getDescription())
        );
    }

    @ExceptionHandler(exception = LoginException.class)
    public GenericResponse<?> handleLoginException(final LoginException e) {
        return new GenericResponse<>(
                LoginException.CODE,
                e.getCurrentTimestamp(),
                new ErrorResponse(LoginException.MESSAGE, e.getDescription())
        );
    }

    @ExceptionHandler(exception = CredentialsException.class)
    public GenericResponse<?> handleCredentialsException(final CredentialsException e) {
        return new GenericResponse<>(
                CredentialsException.CODE,
                e.getCurrentTimestamp(),
                new ErrorResponse(CredentialsException.MESSAGE, e.getDescription())
        );
    }
}
