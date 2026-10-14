package com.pioneers.jpa.utils.mappers;

import com.pioneers.jpa.models.dtos.responses.StudentResponse;
import com.pioneers.jpa.models.entities.Student;

public final class StudentMapper {
    private StudentMapper() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static StudentResponse toStudentResponse(final Student student) {
        return new StudentResponse(student.getFullName(), student.getAge(), student.getEmail(), student.getScore());
    }
}
