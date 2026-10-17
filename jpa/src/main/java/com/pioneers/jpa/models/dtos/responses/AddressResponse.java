package com.pioneers.jpa.models.dtos.responses;

import lombok.Builder;

@Builder
public record AddressResponse(
        String country,
        String governance,
        String city,
        String street,
        int buildingNumber,
        int floorNumber,
        int apartmentNumber
) {
}
