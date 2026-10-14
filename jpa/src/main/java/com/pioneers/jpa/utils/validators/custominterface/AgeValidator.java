package com.pioneers.jpa.utils.validators.custominterface;

import com.pioneers.jpa.utils.validators.custominterface.Age;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgeValidator implements ConstraintValidator<Age, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(Age constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();

        log.debug("initialize(), initialized the AgeValidator class with min = [{}], max = [{}]", min, max);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        log.debug("isValid(), implementing the logic for @Age");
        if (value == null) {
            return false;
        }

        return value >= min && value <= max;
    }
}
