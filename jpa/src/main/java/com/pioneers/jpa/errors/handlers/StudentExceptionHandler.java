package com.pioneers.jpa.errors.handlers;

import com.pioneers.jpa.errors.exceptions.StudentException;
import com.pioneers.jpa.errors.models.ErrorResponse;
import com.pioneers.jpa.errors.models.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class StudentExceptionHandler {

    @ResponseBody
    @ExceptionHandler(exception = StudentException.class)
    public GenericResponse<ErrorResponse> handleStudentException(final StudentException e) {
        return new GenericResponse<>(
                StudentException.CODE,
                e.getCurrentTimestamp(),
                new ErrorResponse(StudentException.MESSAGE, e.getDescription())
        );
    }
}
