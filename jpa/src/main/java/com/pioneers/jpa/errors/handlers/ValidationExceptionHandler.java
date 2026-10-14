package com.pioneers.jpa.errors.handlers;

import com.pioneers.jpa.errors.models.ErrorResponse;
import com.pioneers.jpa.errors.models.GenericResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.pioneers.jpa.utils.times.TimeHelper.currentTimestamp;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public GenericResponse<List<ErrorResponse>> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e
    ) {
        final List<ErrorResponse> errorResponseList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::from)
                .toList();

        return new GenericResponse<>(9000, currentTimestamp(), errorResponseList);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(exception = ConstraintViolationException.class)
    public GenericResponse<List<ErrorResponse>> handleConstraintViolationException(final ConstraintViolationException e) {
        final List<ErrorResponse> errorResponseList = e.getConstraintViolations().stream()
                .toList()
                .stream()
                .map(this::from)
                .toList();
        return new GenericResponse<>(9100, currentTimestamp(), errorResponseList);
    }

    private ErrorResponse from(final ConstraintViolation<?> constraintViolation) {
        final AtomicReference<String> fieldName = new AtomicReference<>();
        constraintViolation.getPropertyPath()
                .forEach(node -> {
                    if (isNodeKindParameter(node.getKind())) {
                        fieldName.set(node.getName());
                    }
                });
        return new ErrorResponse(fieldName.get(), constraintViolation.getMessage());
    }

    private boolean isNodeKindParameter(final ElementKind elementKind) {
        return ElementKind.PARAMETER.equals(elementKind);
    }

    private ErrorResponse from(final FieldError fieldError) {
        return new ErrorResponse(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
