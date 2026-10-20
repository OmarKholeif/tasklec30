package com.pioneers.jpa.repositories;

import com.pioneers.jpa.models.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> findByCountryAndGovernanceAndCityAndStreetAndBuildingNumberAndFloorNumberAndApartmentNumber(
            String country, String governance, String city, String street,
            Integer buildingNumber, Integer floorNumber, Integer apartmentNumber);
}
