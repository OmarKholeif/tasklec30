package com.pioneers.jpa.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @Column(length = 36, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 30, nullable = false)
    private String country;

    @Column(length = 30, nullable = false)
    private String governance;

    @Column(length = 30, nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(name = "building_number", nullable = false)
    private Integer buildingNumber;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @Column(name = "apartment_number", nullable = false)
    private Integer apartmentNumber;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "address")
    private List<Student> students;
}
