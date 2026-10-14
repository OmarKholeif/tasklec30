package com.pioneers.jpa.utils.validators;

@FunctionalInterface
public interface ValidatorService<T> {

    void validate(T t);
}
