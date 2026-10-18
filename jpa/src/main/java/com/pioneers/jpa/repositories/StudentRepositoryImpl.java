//package com.pioneers.jpa.repositories;
//
//import com.pioneers.jpa.errors.exceptions.StudentException;
//import com.pioneers.jpa.models.entities.Student;
//import jakarta.persistence.*;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@Repository
//@RequiredArgsConstructor
//public class StudentRepositoryImpl implements StudentRepository {
//
//    private final EntityManager entityManager;
//
//    @Override
//    @Transactional
//    public void save(final Student student) {
//        final String methodName = "save";
//
//        /*final String nativeSql = "INSERT INTO students (id, fullName, age, email, password, isLoggedIn, score, finalScore, createdAt) VALUES ('"
//                +  student.getId() + "', '" + student.getFullName() +  "', '" + student.getAge() + "', '" + student.getEmail() + "', '";*/
//        /*final String hql = """
//                INSERT INTO Student (id, fullName, age, email, password, isLoggedIn, score, finalScore, createdAt)
//                VALUES (:id, :fullName, :age, :email, :password, :isLoggedIn, :score, :finalScore, :createdAt)
//                """;*/
////        final String sql = "INSERT INTO Student s (id, fullName, age, email, password, isLoggedIn, score, finalScore, createdAt) VALUES (:id, :fullName, :age, :email, :password, :isLoggedIn, :score, :finalScore, :createdAt)";
//
//        entityManager.persist(student);
////        final Query query = entityManager.createQuery(hql);
////        query.setParameter("id", student.getId());
////        query.setParameter("fullName", student.getFullName());
////        query.setParameter("age", student.getAge());
////        query.setParameter("email", student.getEmail());
////        query.setParameter("password", student.getPassword());
////        query.setParameter("isLoggedIn", student.isLoggedIn());
////        query.setParameter("score", student.getScore());
////        query.setParameter("finalScore", student.getFinalScore());
////        query.setParameter("createdAt", student.getCreatedAt());
//
////        query.executeUpdate();
//        log.debug("{}, Saved student with email = [{}] and id = [{}]", methodName, student, student.getId());
//    }
//
//    /*@Override
//    @Transactional
//    public void deleteById(final UUID id) {
//        final String methodName = "delete";
//        final String sql = "DELETE FROM Student s WHERE s.id = :id";
//
//        final Query query = entityManager.createQuery(sql);
//        query.setParameter("id", id);
//
//        query.executeUpdate();
//        log.info("{}, deleting the student with id [{}]", methodName, id);
//    }*/
//
//    /*@Override
//    @Transactional
//    public void deleteAll() {
//        final String methodName = "deleteAll";
//
//        final String sql = "DELETE FROM Student s";
//        entityManager.createQuery(sql).executeUpdate();
//
//        log.info("{}, Successfully deleted all students", methodName);
//    }*/
//
//    @Override
//    @Transactional
//    public void update(final Student student) {
//        final String methodName = "update";
//
//        final String nativeQuery = "UPDATE students SET full_name = :fullName, age = :age, email = :email, " +
//                "password = :password, is_login = :isLogin, score = :score, final_score = :finalScore, " +
//                "created_at = :createdAt WHERE id = :id";
//
//        final Query query = entityManager.createNativeQuery(nativeQuery);
//        query.setParameter("id", student.getId());
//        query.setParameter("fullName", student.getFullName());
//        query.setParameter("age", student.getAge());
//        query.setParameter("email", student.getEmail());
//        query.setParameter("password", student.getPassword());
//        query.setParameter("isLogin", student.isLoggedIn());
//        query.setParameter("score", student.getScore());
//        query.setParameter("finalScore", student.getFinalScore());
//        query.setParameter("createdAt", student.getCreatedAt());
//
//        query.executeUpdate();
//        log.debug("{}, Updated student with email = [{}] and id = [{}]", methodName, student, student.getId());
//    }
//
//    @Override
//    @Transactional
//    public void updateAll(final List<Student> students) {
//        final String methodName = "updateAll";
//
//        students.forEach(this::update);
//        log.info("{}, Successfully updated all students", methodName);
//    }
//
//    @Override
//    public Optional<Student> findByEmail(final String email) throws StudentException {
//        final String sql = "SELECT s FROM Student s WHERE s.email = :email AND s.isArchived = false";
//
//        final TypedQuery<Student> typedQuery = entityManager.createQuery(sql, Student.class);
//        typedQuery.setParameter("email", email);
//
//        try {
//            return Optional.of(typedQuery.getSingleResult());
//        } catch (final NoResultException e) {
//            return Optional.empty();
//        } catch (final NonUniqueResultException e) {
//            throw new StudentException("More than one student with email [" + email + "] was found");
//        }
////        return Optional.ofNullable(typedQuery.getSingleResultOrNull());
//    }
//
//    @Override
//    public Optional<Student> findById(final UUID id) {
////        return Optional.ofNullable(entityManager.find(Student.class, id));
//        final String sql = "SELECT s FROM Student s WHERE s.id = :id AND s.isArchived = false";
//
//        final TypedQuery<Student> typedQuery = entityManager.createQuery(sql, Student.class);
//        typedQuery.setParameter("id", id);
//
//        try {
//            return Optional.of(typedQuery.getSingleResult());
//        } catch (final NoResultException e) {
//            return Optional.empty();
//        } catch (final NonUniqueResultException e) {
//            throw new StudentException("More than one student with was found");
//        }
//    }
//
//    @Override
//    public Collection<Student> findAllSortedByAge() {
//        return entityManager
//                .createQuery("SELECT s FROM Student s LEFT JOIN FETCH s.address WHERE s.isArchived = FALSE ORDER BY s.age ASC", Student.class)
//                .getResultList();
//    }
//
//    @Override
//    public Collection<Student> findAllSucceeded() {
//        return entityManager.createQuery(
//                        "SELECT s FROM Student s WHERE s.score > s.finalScore * 0.5 AND s.isArchived = FALSE", Student.class)
//                .getResultList();
//    }
//}
