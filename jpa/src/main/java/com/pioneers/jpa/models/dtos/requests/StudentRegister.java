package com.pioneers.jpa.models.dtos.requests;

import com.pioneers.jpa.utils.validators.custominterface.Age;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StudentRegister(
        @NotBlank(message = "{validation.first-name.blank}")
        @Size(min = 2, max = 15, message = "{validation.first-name.error.message}")
        String firstName,

        @NotBlank(message = "{validation.second-name.blank}")
        @Size(min = 2, max = 15, message = "{validation.second-name.size}")
        String secondName,

        @Age(min = 18, max = 25)
        int age,

        @Email(message = "{validation.email.pattern}")
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s])\\S{9,31}$",
                message = "{validation.password.pattern}")
        String password,

        AddressRequest addressRequest
) {
}
