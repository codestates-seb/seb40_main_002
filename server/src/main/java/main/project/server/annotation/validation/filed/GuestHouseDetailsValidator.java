package main.project.server.annotation.validation.filed;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GuestHouseDetailsValidator implements ConstraintValidator<GuestHouseDetails, Boolean[]> {
    @Override
    public void initialize(GuestHouseDetails constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Boolean[] value, ConstraintValidatorContext context) {

        if(value == null || value.length != 10) return false;

        for (Object detail : value) {
            if (!(detail instanceof Boolean)) {
                return false;
            }
        }

        return true;
    }
}
