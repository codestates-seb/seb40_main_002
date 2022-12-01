package main.project.server.annotation.validation.filed;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TagValidator implements ConstraintValidator <ValidTag, String>{

    @Override
    public void initialize(ValidTag constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null) return false;

        if (value.charAt(0) != '|' || value.charAt(value.length() - 1) != '|') {
            return false;
        }

        return true;
    }
}
