package com.pioneers.jpa.controllers;

import com.pioneers.jpa.models.dtos.requests.StudentLogin;
import com.pioneers.jpa.models.dtos.requests.StudentRegister;
import com.pioneers.jpa.models.dtos.responses.GenericResponse;
import com.pioneers.jpa.services.students.AuthStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthStudentService authStudentService;

    @PostMapping("signup")
    public String registerStudentApi(
            @RequestBody final StudentRegister studentRegisterRequest
    ) {
        final String methodName = "registerStudentApi";
        log.info("{}, Implementing Registration flow for [{}]", methodName, studentRegisterRequest.email());

        authStudentService.signup(studentRegisterRequest);

        log.info("{}, Successfully registered student with email [{}]", methodName, studentRegisterRequest.email());

        return "Successfully registered student with email: " + studentRegisterRequest.email();
    }

    @PostMapping("login")
    public String loginApi(@RequestBody @Valid StudentLogin studentLoginRequest) {
        authStudentService.login(studentLoginRequest);

        return "Student with email: " + studentLoginRequest.email() + " logged in successfully!!";
    }

    @PostMapping("logout")
    public String logoutApi(@RequestParam final String email) {
        authStudentService.logout(email);

        return "Successfully logged out!";
    }

    // TODO: Apply the validation for this API
    @PostMapping("saveAll")
    public GenericResponse<?> saveAllApi(@RequestBody List<StudentRegister> studentRegisterRequests) {
        return authStudentService.saveAll(studentRegisterRequests);
    }
}
