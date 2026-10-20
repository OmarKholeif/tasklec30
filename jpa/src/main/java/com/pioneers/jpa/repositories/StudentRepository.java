package com.pioneers.jpa.repositories;

import com.pioneers.jpa.models.entities.Student;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Student s SET s.isArchived = TRUE, s.isLoggedIn = FALSE WHERE s.id = :id")
    void deleteById(@Param("id") @NonNull UUID id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Student s SET s.isArchived = TRUE, s.isLoggedIn = FALSE")
    void deleteAll();

    @Transactional
    @Modifying
    @Query("""
            UPDATE Student s
            SET s.fullName = :#{#student.fullName},
                s.age = :#{#student.age},
                s.email = :#{#student.email},
                s.password = :#{#student.password},
                s.isLoggedIn = :#{#student.isLoggedIn()},
                s.score = :#{#student.score},
                s.finalScore = :#{#student.finalScore},
                s.createdAt = :#{#student.createdAt},
                s.isArchived = :#{#student.isArchived},
                s.address = :#{#student.address}
            WHERE s.id = :#{#student.id}
            """)
    void update(@Param("student") Student student);

    Optional<Student> findByEmail(String email);

    List<Student> findAllByFullNameAndAgeGreaterThan(String fullName, int ageIsGreaterThan);

    Collection<Student> findAllByIsArchivedIsFalseOrderByAgeAsc();

    Optional<Student> findByEmailAndIsArchivedIsFalse(String email);

    Optional<Student> findFirstByOrderByCreatedAtAsc();

    @Query("""
            SELECT s
            FROM Student s
            WHERE s.score > s.finalScore * 0.5
              AND s.isArchived = FALSE
            """)
    Collection<Student> findAllSucceeded();
}
