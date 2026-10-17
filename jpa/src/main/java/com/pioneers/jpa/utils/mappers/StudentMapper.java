package com.pioneers.jpa.utils.mappers;

import com.pioneers.jpa.errors.exceptions.CredentialsException;
import com.pioneers.jpa.models.dtos.requests.StudentRegister;
import com.pioneers.jpa.models.dtos.responses.AddressResponse;
import com.pioneers.jpa.models.dtos.responses.StudentResponse;
import com.pioneers.jpa.models.entities.Address;
import com.pioneers.jpa.models.entities.Student;
import com.pioneers.jpa.utils.times.TimeHelper;

import java.util.Optional;

import static com.pioneers.jpa.utils.CredentialsHelper.hashPassword;
import static com.pioneers.jpa.utils.NameBuilder.buildFullName;
import static com.pioneers.jpa.utils.mappers.AddressMapper.toAddress;
import static com.pioneers.jpa.utils.mappers.AddressMapper.toAddressResponse;

public final class StudentMapper {
    private StudentMapper() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static StudentResponse toStudentResponse(final Student student) {
        final Optional<AddressResponse> optionalAddressResponse = toAddressResponse(student.getAddress());

        return new StudentResponse(student.getFullName(), student.getAge(), student.getEmail(), student.getScore(),
                optionalAddressResponse);
    }

    public static Student toStudent(final StudentRegister studentRegister) throws CredentialsException {
        final String fullName = buildFullName(studentRegister.firstName(), studentRegister.secondName());
        final String hashedPassword = hashPassword(studentRegister.password());
        final Address address = toAddress(studentRegister.addressRequest());

        return Student.builder()
                .fullName(fullName)
                .age(studentRegister.age())
                .email(studentRegister.email())
                .password(hashedPassword)
                .createdAt(TimeHelper.currentTimestamp())
                .isArchived(false)
                .address(address)
                .build();
    }
}
