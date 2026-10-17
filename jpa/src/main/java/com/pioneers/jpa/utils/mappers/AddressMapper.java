package com.pioneers.jpa.utils.mappers;

import com.pioneers.jpa.models.dtos.requests.AddressRequest;
import com.pioneers.jpa.models.entities.Address;

public final class AddressMapper {
    private AddressMapper() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static Address toAddress(final AddressRequest addressRequest) {
        return Address.builder()
                .country(addressRequest.country())
                .governance(addressRequest.governance())
                .city(addressRequest.city())
                .street(addressRequest.street())
                .buildingNumber(addressRequest.buildingNumber())
                .floorNumber(addressRequest.floor())
                .apartmentNumber(addressRequest.apartmentNumber())
                .build();
    }
}
