package main.project.server.annotation.validation.role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class RoleValidator implements ConstraintValidator<ValidRole, List<String>> {

    @Override
    public void initialize(ValidRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value == null || value.size() == 0) return false;
        for (String role : value) {
            if (!role.equals("USER") && !role.equals("ADMIN")) return false;
        }
        return true;
    }
}
