package com.pioneers.jpa.services.students;

import com.pioneers.jpa.errors.exceptions.StudentException;
import com.pioneers.jpa.models.dtos.requests.StudentUpdate;
import com.pioneers.jpa.models.dtos.responses.StudentResponse;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface StudentService {

    List<StudentResponse> findAllSortedByAge() throws StudentException;

    StudentResponse findById(UUID id) throws StudentException;

    StudentResponse findFirstRegisteredStudent() throws StudentException;

    Collection<StudentResponse> findAllSucceeded() throws StudentException;

    List<StudentResponse> findAllByFullName(String fullName) throws StudentException;

    void update(UUID id, StudentUpdate studentUpdateRequest) throws StudentException;

    void deleteById(UUID id) throws StudentException;

    void deleteAll();
}
