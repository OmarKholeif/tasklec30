package com.pioneers.jpa;

import com.pioneers.jpa.models.entities.Address;
import com.pioneers.jpa.models.entities.Student;
import com.pioneers.jpa.repositories.StudentRepository;

import jakarta.persistence.EntityManager;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static com.pioneers.jpa.utils.CredentialsHelper.hashPassword;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(properties = {
        "spring.main.lazy-initialization=false",
        "spring.datasource.url=jdbc:h2:mem:jpa-tests;MODE=PostgreSQL;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class JpaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private Validator validator;

    @Test
    @Sql(statements = "DELETE FROM students WHERE email = 'abdelaziz.said@techpionershub.com'",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void registerStudentPersistsStudentAndAddressWithoutAnExistingTransaction() throws Exception {
        mockMvc.perform(post("/jpa/auth/signup").contextPath("/jpa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Abdelaziz",
                                    "secondName": "Said",
                                    "age": 23,
                                    "email": "abdelaziz.said@techpionershub.com",
                                    "password": "P@ssw0rd123",
                                    "addressRequest": {
                                        "country": "Arab Republic of Egypt",
                                        "governance": "Cairo",
                                        "city": "Fifth Setllement",
                                        "street": "Mohamed Nagib",
                                        "buildingNumber": 100,
                                        "floor": 1,
                                        "apartmentNumber": 1
                                    }
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "Successfully registered student with email: abdelaziz.said@techpionershub.com"));

        Student registeredStudent = entityManager.createQuery(
                        "SELECT s FROM Student s WHERE s.email = :email", Student.class)
                .setParameter("email", "abdelaziz.said@techpionershub.com")
                .getSingleResult();

        assertThat(registeredStudent.getId()).isNotNull();
        assertThat(registeredStudent.getFullName()).isEqualTo("Abdelaziz Said");
        assertThat(registeredStudent.getAge()).isEqualTo(23);
        assertThat(registeredStudent.getPassword()).isEqualTo(hashPassword("P@ssw0rd123"));
        assertThat(registeredStudent.isLoggedIn()).isFalse();
        assertThat(registeredStudent.getIsArchived()).isFalse();
        assertThat(studentRepository.findByEmail(registeredStudent.getEmail())).isPresent();
        assertThat(studentRepository.findById(registeredStudent.getId())).isPresent();

        Address registeredAddress = entityManager.createQuery(
                        "SELECT a FROM Student s JOIN s.address a WHERE s.id = :id", Address.class)
                .setParameter("id", registeredStudent.getId())
                .getSingleResult();

        assertThat(registeredAddress.getId()).isNotNull();
        assertThat(registeredAddress.getCountry()).isEqualTo("Arab Republic of Egypt");
        assertThat(registeredAddress.getGovernance()).isEqualTo("Cairo");
        assertThat(registeredAddress.getCity()).isEqualTo("Fifth Setllement");
        assertThat(registeredAddress.getStreet()).isEqualTo("Mohamed Nagib");
        assertThat(registeredAddress.getBuildingNumber()).isEqualTo(100);
        assertThat(registeredAddress.getFloorNumber()).isEqualTo(1);
        assertThat(registeredAddress.getApartmentNumber()).isEqualTo(1);
    }

    /*@Test
    @Transactional
    void findAllStudentsReturnsStudentsSortedByAge() throws Exception {
        entityManager.persist(student("Older Student", 24, "older@example.com"));
        entityManager.persist(student("Younger Student", 19, "younger@example.com"));
        entityManager.flush();
        entityManager.createNativeQuery("""
                        INSERT INTO students (id, full_name, age, email, password, is_login)
                        VALUES (:id, 'No Scores Student', 21, 'no-scores@example.com', 'test-password', false)
                        """)
                .setParameter("id", UUID.randomUUID())
                .executeUpdate();
        entityManager.clear();

        mockMvc.perform(get("/jpa/student/findAll").contextPath("/jpa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].fullName").value("Younger Student"))
                .andExpect(jsonPath("$[0].age").value(19))
                .andExpect(jsonPath("$[0].email").value("younger@example.com"))
                .andExpect(jsonPath("$[1].fullName").value("No Scores Student"))
                .andExpect(jsonPath("$[1].age").value(21))
                .andExpect(jsonPath("$[2].age").value(24));
    }*/

    @Test
    void validationProviderEnforcesConstraints() {
        assertThat(validator.validate(new ValidationProbe(""))).hasSize(1);
        assertThat(validator.validate(new ValidationProbe("valid"))).isEmpty();
    }

    private static Student student(String fullName, int age, String email) {
        return Student.builder()
                .fullName(fullName)
                .age(age)
                .email(email)
                .password("test-password")
                .build();
    }

    private record ValidationProbe(@NotBlank String value) {
    }
}
