package com.pioneers.jpa.models.dtos.responses;

import java.util.Optional;

public record StudentResponse(
        String fullName,
        int age,
        String email,
        Float score,
        Optional<AddressResponse> optionalAddressResponse
) {
}
