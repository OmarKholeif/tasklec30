package com.pioneers.jpa.repositories;

import com.pioneers.jpa.models.entities.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentRepository {

    void save(Student student);

//    void deleteById(UUID id);

//    void deleteAll();

    void update(Student student);

    void updateAll(List<Student> students);

    Optional<Student> findByEmail(String email);

    Optional<Student> findById(UUID id);

    Collection<Student> findAllSortedByAge();

    Collection<Student> findAllSucceeded();
}
