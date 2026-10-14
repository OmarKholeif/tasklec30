package com.pioneers.jpa.models.dtos.requests;

import com.pioneers.jpa.utils.validators.custominterface.Age;
import jakarta.validation.constraints.*;

public record StudentUpdate(
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

        @Min(value = 0, message = "Min Score is 0")
        @Max(value = 100, message = "Max Score is 100")
        float score,

        float finalScore
) {
    @Override
    public String toString() {
        return "StudentUpdate{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", score=" + score +
                ", password='***********" + '\'' +
                '}';
    }
}
