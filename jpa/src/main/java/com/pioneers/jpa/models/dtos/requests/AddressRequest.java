package com.pioneers.jpa.models.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(
        @NotBlank(message = "Country must not be blank")
        @Size(min = 2, max = 30, message = "Country length between 2 and 30")
        String country,

        @NotBlank(message = "Governance must not be blank")
        @Size(min = 2, max = 30, message = "Governance length between 2 and 30")
        String governance,

        @NotBlank(message = "City must not be blank")
        @Size(min = 2, max = 30, message = "City length between 2 and 30")
        String city,

        @NotBlank(message = "Street must not be blank")
        @Size(min = 2, max = 255, message = "Street length between 2 and 30")
        String street,

        @Min(value = 1, message = "Min building number is 1")
        @Max(value = 1000, message = "Max building number are 1000")
        int buildingNumber,

        @Min(value = 1, message = "Min floors are 1")
        @Max(value = 50, message = "Max floors are 50")
        int floor,

        @Min(value = 1, message = "Min apartments are 1")
        @Max(value = 50, message = "Max apartments are 200")
        int apartmentNumber
) {
}
