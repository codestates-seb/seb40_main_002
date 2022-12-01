package main.project.server.annotation.validation.filed;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddressValidator implements ConstraintValidator<ValidAddress, String[]> {

    @Override
    public void initialize(ValidAddress constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        if(value == null || value.length != 3) return false;

        return true;
    }
}
