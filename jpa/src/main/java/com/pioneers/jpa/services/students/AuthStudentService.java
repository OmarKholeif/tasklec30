package com.pioneers.jpa.services.students;

import com.pioneers.jpa.errors.exceptions.CredentialsException;
import com.pioneers.jpa.errors.exceptions.LoginException;
import com.pioneers.jpa.errors.exceptions.LogoutException;
import com.pioneers.jpa.errors.exceptions.RegisterException;
import com.pioneers.jpa.models.dtos.requests.StudentLogin;
import com.pioneers.jpa.models.dtos.requests.StudentRegister;
import com.pioneers.jpa.models.dtos.responses.GenericResponse;
import jakarta.validation.constraints.Email;

import java.util.List;

public interface AuthStudentService {

    void signup(StudentRegister studentRegisterRequest) throws RegisterException;

    void login(StudentLogin studentLoginRequest) throws LoginException, CredentialsException;

    void logout(@Email(message = "{validation.email.pattern}") String email) throws LogoutException;

    GenericResponse<?> saveAll(final List<StudentRegister> studentRegisterRequests) throws RegisterException;
}
