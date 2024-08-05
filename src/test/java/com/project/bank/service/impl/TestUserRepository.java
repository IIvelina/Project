package com.project.bank.service.impl;

import com.project.bank.model.entity.User;
import com.project.bank.model.enums.UserGenderEnum;
import com.project.bank.model.enums.UserRoleEnum;
import com.project.bank.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static com.project.bank.service.impl.BankUserDetailsServiceTest.TEST_USERNAME;

public class TestUserRepository implements UserRepository {

    @Override
    public boolean existsByClientNumber(String clientNumber) {
        return false;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        if (Objects.equals(username, TEST_USERNAME)) {
            User testUser = new User();
            testUser.setUsername(TEST_USERNAME);
            testUser.setPassword("topsecret");
            testUser.setEmail("dilyan@abv.bg");
            testUser.setFullName("Dilyan Dilyanov");
            testUser.setGender(UserGenderEnum.MALE);
            testUser.setSsn("6541239871");
            testUser.setPhoneNumber("0877954569");
            testUser.setIdCardNumber("741963258");

            return Optional.of(testUser);
        }

        return Optional.empty();
    }

    @Override
    public boolean existsBySsn(String ssn) {
        return false;
    }

    @Override
    public boolean existsByIdCardNumber(String idCardNumber) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return false;
    }

    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return Optional.empty();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Long aLong) {
        return null;
    }

    @Override
    public User getById(Long aLong) {
        return null;
    }

    @Override
    public User getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends User> S save(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }
}
