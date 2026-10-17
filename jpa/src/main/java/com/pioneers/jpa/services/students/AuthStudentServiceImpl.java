package com.pioneers.jpa.services.students;

import com.pioneers.jpa.errors.exceptions.CredentialsException;
import com.pioneers.jpa.errors.exceptions.LoginException;
import com.pioneers.jpa.errors.exceptions.LogoutException;
import com.pioneers.jpa.errors.exceptions.RegisterException;
import com.pioneers.jpa.models.dtos.requests.StudentLogin;
import com.pioneers.jpa.models.dtos.requests.StudentRegister;
import com.pioneers.jpa.models.dtos.responses.GenericResponse;
import com.pioneers.jpa.models.dtos.responses.StudentResponse;
import com.pioneers.jpa.models.entities.Student;
import com.pioneers.jpa.repositories.StudentRepository;
import com.pioneers.jpa.utils.mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pioneers.jpa.utils.CredentialsHelper.hashPassword;
import static com.pioneers.jpa.utils.mappers.StudentMapper.toStudent;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AuthStudentServiceImpl implements AuthStudentService {

    private final StudentRepository studentRepository;

    // TODO: Fix the found bug in the following service.
    //  The bug occurs when we signup a student who already deleted his account.
    @Override
    public void signup(final StudentRegister studentRegisterRequest) throws RegisterException {
        final String methodName = "signup";

        final Optional<Student> optionalStudent = studentRepository.findByEmail(studentRegisterRequest.email());

        if (optionalStudent.isPresent()) {
            final String logMessage =
                    String.format("%s, Errors in registration for email [%s], due to account is already registered",
                            methodName, studentRegisterRequest.email());

            throw new RegisterException(
                    String.format("Cannot find email [%s]", studentRegisterRequest.email()), logMessage);
        }

        final Student student;
        try {
            student = toStudent(studentRegisterRequest);
        } catch (final CredentialsException e) {
            final String logMessage = String.format("%s, Cannot hash the [%s] user password due to: [%s]",
                    methodName, studentRegisterRequest.email(), e.getMessage());

            throw new RegisterException(e.getMessage(), logMessage);
        }

        studentRepository.save(student);

        log.debug("{}, Successfully registered student with email [{}]", methodName, student.getEmail());
    }

    @Override
    public void login(final StudentLogin studentLoginRequest) throws LoginException, CredentialsException {

        final Student foundStudent = studentRepository.findByEmail(studentLoginRequest.email())
                .orElseThrow(() ->
                        new LoginException("Student with email: " + studentLoginRequest.email() + " not registered"));

        if (foundStudent.isLoggedIn()) {
            throw new LoginException("Student already logged in");
        }

        final String hashedPassword = hashPassword(studentLoginRequest.password());

        if (!hashedPassword.equals(foundStudent.getPassword())) {
            throw new LoginException("Passwords don't match");
        }

        foundStudent.login();

        studentRepository.update(foundStudent);
    }

    @Override
    public void logout(final String email) throws LogoutException {
        final Student foundStudent = studentRepository.findByEmail(email)
                .orElseThrow(() -> new LogoutException("Student with email: " + email + " not registered"));

        foundStudent.logout();

        studentRepository.update(foundStudent);
    }

    @Override
    public GenericResponse<?> saveAll(final List<StudentRegister> studentRegisterRequests) throws RegisterException {

        final List<Student> registeredStudents = new ArrayList<>();

        studentRegisterRequests.forEach(request -> {
            Optional<Student> optionalStudent = studentRepository.findByEmail(request.email());

            if (optionalStudent.isPresent()) {
                registeredStudents.add(optionalStudent.get());
                return;
            }
            signup(request);
        });

        if (registeredStudents.isEmpty()) {
            return new GenericResponse<>("Successfully all registeredStudents successfully!", Optional.empty());
        }

        final List<StudentResponse> rejectedStudentsList = registeredStudents.stream()
                .map(StudentMapper::toStudentResponse)
                .toList();

        return new GenericResponse<>("Those list are rejected to be inserted", rejectedStudentsList);
    }
}
