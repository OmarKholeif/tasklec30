package com.pioneers.jpa.services.students;

import com.pioneers.jpa.errors.exceptions.StudentException;
import com.pioneers.jpa.models.dtos.requests.StudentUpdate;
import com.pioneers.jpa.models.dtos.responses.StudentResponse;
import com.pioneers.jpa.models.entities.Student;
import com.pioneers.jpa.repositories.StudentRepository;
import com.pioneers.jpa.utils.mappers.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.pioneers.jpa.utils.CredentialsHelper.hashPassword;
import static com.pioneers.jpa.utils.NameBuilder.buildFullName;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponse> findAllSortedByAge() throws StudentException {
        final String methodName = "findAllSortedByAge";
        final Collection<Student> sortedStudentsByAge = studentRepository.findAllSortedByAge();

        log.debug("{}, Successfully fetched all students sorted by age", methodName);

        if (sortedStudentsByAge.isEmpty()) {
            log.error("{}, No students found in the DB!", methodName);
            throw new StudentException("No students found in the DB");
        }

        final List<StudentResponse> studentResponses = sortedStudentsByAge.stream()
                .map(StudentMapper::toStudentResponse)
                .toList();

        log.debug("{}, Successfully mapped the sorted students by age to studentResponses with size: [{}]",
                methodName, sortedStudentsByAge.size());

        return studentResponses;
    }

    @Override
    public StudentResponse findById(final UUID id) throws StudentException {
        final Optional<Student> optionalFoundStudent = studentRepository.findById(id);

        if (optionalFoundStudent.isEmpty()) {
            throw new StudentException("Student doesn't exist in the DB");
        }

        return StudentMapper.toStudentResponse(optionalFoundStudent.get());
    }

    @Override
    public Collection<StudentResponse> findAllSucceeded() throws StudentException {
        final Collection<Student> succeededStudents = studentRepository.findAllSucceeded();

        if (succeededStudents.isEmpty()) {
            throw new StudentException("No success students found in the DB");
        }

        return succeededStudents.stream()
                .map(StudentMapper::toStudentResponse)
                .toList();
    }

    @Override
    public void update(final UUID id, final StudentUpdate studentUpdateRequest) throws StudentException {

        final Optional<Student> optionalFoundStudent = studentRepository.findById(id);

        if (optionalFoundStudent.isEmpty()) {
            throw new StudentException("Student doesn't exist in the DB");
        }

        final String updatedFullName = buildFullName(studentUpdateRequest.firstName(), studentUpdateRequest.secondName());
        final String updatedHashedPassword = hashPassword(studentUpdateRequest.password());

        final Student foundStudent = optionalFoundStudent.get();
        final Student updatedStudent = Student.builder()
                .id(foundStudent.getId())
                .fullName(updatedFullName)
                .age(foundStudent.getAge())
                .email(foundStudent.getEmail())
                .password(updatedHashedPassword)
                .isLoggedIn(foundStudent.isLoggedIn())
                .score(studentUpdateRequest.score())
                .finalScore(studentUpdateRequest.finalScore())
                .createdAt(foundStudent.getCreatedAt())
                .isArchived(foundStudent.getIsArchived())
                .build();

        studentRepository.update(updatedStudent);
    }

    @Override
    public void deleteById(final UUID id) throws StudentException {
        final Optional<Student> optionalFoundStudent = studentRepository.findById(id);

        if (optionalFoundStudent.isEmpty()) {
            throw new StudentException("No success students found in the DB");
        }

        final Student foundStudent = optionalFoundStudent.get();
        foundStudent.setLoggedIn(false);
        foundStudent.setIsArchived(true);

        studentRepository.update(foundStudent);
    }

    @Override
    public void deleteAll() {
        final List<Student> deletedStudents = studentRepository.findAllSortedByAge()
                .stream()
                .peek(student -> student.setIsArchived(true))
                .toList();

        studentRepository.updateAll(deletedStudents);
    }
}
