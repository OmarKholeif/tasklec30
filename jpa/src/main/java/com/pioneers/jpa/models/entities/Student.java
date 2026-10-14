package com.pioneers.jpa.models.entities;

import com.pioneers.jpa.errors.exceptions.LogoutException;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "students")
public class Student {
    @Id
    @Column(length = 36, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "full_name", length = 31, nullable = false)
    private String fullName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_login", nullable = false)
    private boolean isLoggedIn;

    @Column
    private Float score;

    @Column(name = "final_score")
    private Float finalScore;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_archived")
    private Boolean isArchived;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    public void login() {
        isLoggedIn = true;
    }

    public void logout() {
        if (!isLoggedIn) {
            throw new LogoutException("Student not logged in");
        }

        isLoggedIn = false;
    }

    @Override
    public String toString() {
        return "Student{" +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password=**********" +
                ", isLoggedIn=" + isLoggedIn +
                ", score=" + score +
                ", finalScore=" + finalScore +
                '}';
    }
}
