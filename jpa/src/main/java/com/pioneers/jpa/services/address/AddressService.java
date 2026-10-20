package com.pioneers.jpa.services.address;

import com.pioneers.jpa.models.entities.Address;
import com.pioneers.jpa.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address findOrCreateAddress(final Address requestedAddress) {
        if (requestedAddress == null) {
            return null;
        }

        return addressRepository
                .findByCountryAndGovernanceAndCityAndStreetAndBuildingNumberAndFloorNumberAndApartmentNumber(
                        requestedAddress.getCountry(),
                        requestedAddress.getGovernance(),
                        requestedAddress.getCity(),
                        requestedAddress.getStreet(),
                        requestedAddress.getBuildingNumber(),
                        requestedAddress.getFloorNumber(),
                        requestedAddress.getApartmentNumber())
                .orElseGet(() -> addressRepository.save(requestedAddress));
    }
}
